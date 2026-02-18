package com.fiap.gateway;

import com.fiap.application.gateway.PartGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.mapper.part.PartMapper;
import com.fiap.persistence.entity.PartEntity;
import com.fiap.persistence.repository.PartJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PartGatewayImpl implements PartGateway {

    private final PartJpaRepository repository;
    private final PartMapper partMapper;

    public PartGatewayImpl(PartJpaRepository repository, PartMapper partMapper) {
        this.repository = repository;
        this.partMapper = partMapper;
    }

    @Override
    public Part findById(UUID id) throws NotFoundException, BusinessRuleException {
        PartEntity partEntity = repository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCodeEnum.STOCK0001.getMessage(), ErrorCodeEnum.STOCK0001.getCode()));
        return partMapper.toDomain(partEntity);
    }

    @Override
    public void save(Part part) {
        repository.save(partMapper.toEntity(part));
    }

    @Override
    public Part create(Part part) throws BusinessRuleException {
        PartEntity entity = partMapper.toEntity(part);
        PartEntity savedEntity = repository.save(entity);
        return partMapper.toDomain(savedEntity);
    }

    @Override
    public void saveAll(List<Part> parts) {
        List<PartEntity> partEntities = parts.stream().map(partMapper::toEntity).toList();
        repository.saveAll(partEntities);
    }

    @Override
    public Part update(Part part) throws BusinessRuleException {
        PartEntity entity = partMapper.toEntity(part);
        PartEntity savedEntity = repository.save(entity);
        return partMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}

