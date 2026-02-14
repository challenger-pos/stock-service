package com.fiap.usecase;

import com.fiap.core.domain.ReservationItem;
import com.fiap.core.exception.BusinessRuleException;

import java.util.List;

public interface CancelStockReservationUseCase {
    void execute(List<ReservationItem> items) throws BusinessRuleException;
}
