package com.fiap.usecase;

import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;

import java.util.UUID;

public interface ReserveStockUseCase {
    void execute(UUID partId, int quantity) throws NotFoundException, BusinessRuleException;
}

