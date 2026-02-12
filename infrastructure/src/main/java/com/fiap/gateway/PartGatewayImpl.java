package com.fiap.gateway;

import com.fiap.application.gateway.PartGateway;
import com.fiap.core.domain.Part;
import com.fiap.persistence.entity.PartEntity;
import com.fiap.persistence.repository.PartJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PartGatewayImpl implements PartGateway {

    private final PartJpaRepository repository;

    public PartGatewayImpl(PartJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Part> findById(UUID id) {
        return repository.findById(id).map(PartEntity::toDomain);
    }

    @Override
    public void save(Part part) {
        repository.save(PartEntity.fromDomain(part));
    }
}

