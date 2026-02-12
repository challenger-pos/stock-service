package com.fiap.usecase;

import com.fiap.core.events.WorkOrderStockRequestedEvent;

public interface ProcessStockRequestUseCase {
    void execute(WorkOrderStockRequestedEvent event);
}