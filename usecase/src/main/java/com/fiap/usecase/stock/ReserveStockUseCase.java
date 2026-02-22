package com.fiap.usecase.stock;

import com.fiap.core.domain.ReservationItem;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ReserveStockUseCase {
    void execute(UUID workOrderId, List<ReservationItem> items) throws NotFoundException, BusinessRuleException;
}

