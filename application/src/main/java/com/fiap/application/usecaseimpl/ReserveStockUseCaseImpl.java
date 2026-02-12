package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.PartGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.ReserveStockUseCase;

import java.util.UUID;

public class ReserveStockUseCaseImpl implements ReserveStockUseCase {

    private final PartGateway gateway;

    public ReserveStockUseCaseImpl(PartGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(UUID partId, int quantity) throws NotFoundException, BusinessRuleException {
        Part part = gateway.findById(partId)
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.STOCK0001.getMessage(), ErrorCodeEnum.STOCK0001.getCode()));

        part.getStock().reserve(quantity);

        gateway.save(part);
    }
}

