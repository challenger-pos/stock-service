package com.fiap.usecase.stock;

import com.fiap.core.domain.ReservationItem;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;

import java.util.List;

public interface CancelStockReservationUseCase {
    void execute(List<ReservationItem> items) throws BusinessRuleException, NotFoundException;
}
