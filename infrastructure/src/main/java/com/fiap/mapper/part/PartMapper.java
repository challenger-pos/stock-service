package com.fiap.mapper.part;

import com.fiap.core.domain.Money;
import com.fiap.core.domain.Part;
import com.fiap.core.domain.Stock;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.dto.part.CreatePartRequest;
import com.fiap.dto.part.PartResponse;
import com.fiap.dto.part.UpdatePartRequest;
import com.fiap.persistence.entity.PartEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PartMapper {

    public Part toDomain(CreatePartRequest request) throws BusinessRuleException {
        return Part.builder()
                .name(request.name())
                .price(Money.of(request.price()))
                .stock(Stock.of(request.stockQuantity(), 0))
                .build();
    }

    public Part toDomain(UUID id, UpdatePartRequest request) throws BusinessRuleException {
        return Part.builder()
                .id(id)
                .name(request.name())
                .price(Money.of(request.price()))
                .stock(Stock.of(request.stockQuantity(), request.reservedStock()))
                .build();
    }

    public PartResponse toResponse(Part part) {
        return new PartResponse(
                part.getId(),
                part.getName(),
                part.getPrice().getValue(),
                part.getStock().getQuantity(),
                part.getStock().getReserved(),
                part.getCreatedAt(),
                part.getUpdatedAt()
        );
    }

    public PartEntity toEntity(Part part) {
        return PartEntity.builder()
                .id(part.getId())
                .name(part.getName())
                .price(part.getPrice().getValue())
                .quantity(part.getStock().getQuantity())
                .reserved(part.getStock().getReserved())
                .createdAt(part.getCreatedAt())
                .updatedAt(part.getUpdatedAt())
                .build();
    }

    public Part toDomain(PartEntity entity) throws BusinessRuleException {
        try {
            return Part.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .price(Money.of(entity.getPrice()))
                    .stock(Stock.of(entity.getQuantity(), entity.getReserved()))
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .build();
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException(ErrorCodeEnum.STOCK0006.getMessage(), ErrorCodeEnum.STOCK0006.getCode());
        }
    }
}