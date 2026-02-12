package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.StockEventPublisherGateway;
import com.fiap.application.gateway.StockGateway;
import com.fiap.core.events.StockFailedEvent;
import com.fiap.core.events.StockReservedEvent;
import com.fiap.core.events.WorkOrderStockRequestedEvent;
import com.fiap.usecase.ProcessStockRequestUseCase;

public class ProcessStockRequestUseCaseImpl implements ProcessStockRequestUseCase {

    private final StockGateway stockGateway;
    private final StockEventPublisherGateway publisher;

    public ProcessStockRequestUseCaseImpl(StockGateway stockGateway,
                                          StockEventPublisherGateway publisher) {
        this.stockGateway = stockGateway;
        this.publisher = publisher;
    }

    @Override
    public void execute(WorkOrderStockRequestedEvent event) {
        boolean success = stockGateway.reserve(event.items());

        if (success) {
            publisher.publishStockReserved(new StockReservedEvent(event.workOrderId()));
        } else {
            publisher.publishStockFailed(
                    new StockFailedEvent(event.workOrderId(), "Estoque insuficiente")
            );
        }

    }
}
