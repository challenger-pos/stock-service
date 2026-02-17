package com.fiap.application.usecaseimpl.stock;

import com.fiap.application.gateway.PartGateway;
import com.fiap.application.gateway.StockEventPublisherGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.domain.ReservationItem;
import com.fiap.core.events.StockFailedEvent;
import com.fiap.core.events.StockReservedEvent;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.stock.ReserveStockUseCase;

import java.util.List;
import java.util.UUID;

public class ReserveStockUseCaseImpl implements ReserveStockUseCase {

    private final PartGateway partGateway;
    private final StockEventPublisherGateway publisher;

    public ReserveStockUseCaseImpl(PartGateway partGateway,
                                   StockEventPublisherGateway publisher) {
        this.partGateway = partGateway;
        this.publisher = publisher;
    }

    @Override
    public void execute(UUID workOrderId, List<ReservationItem> items) {

        try {
            for (ReservationItem item : items) {
                Part part = partGateway.findById(item.partId())
                        .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.STOCK0001.getMessage(), ErrorCodeEnum.STOCK0001.getCode()));

                part.getStock().reserve(item.quantity());
            }

            for (ReservationItem item : items) {
                Part part = partGateway.findById(item.partId()).get();
                part.getStock().reserve(item.quantity());
                partGateway.save(part);
            }

            publisher.publishStockReserved(new StockReservedEvent(workOrderId));

        } catch (Exception ex) {
            publisher.publishStockFailed(new StockFailedEvent(workOrderId, ex.getMessage()));
        }
    }
}

