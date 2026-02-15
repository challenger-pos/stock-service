package com.fiap.message;

import com.fiap.core.domain.ReservationItem;
import com.fiap.core.events.StockApprovedEvent;
import com.fiap.core.events.StockCancelRequestedEvent;
import com.fiap.core.events.StockRequestedEvent;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.CancelStockReservationUseCase;
import com.fiap.usecase.EffectiveStockReservationUseCase;
import com.fiap.usecase.ReserveStockUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StockRequestListener {

    private final ReserveStockUseCase reserveStockUseCase;
    private final EffectiveStockReservationUseCase effectiveStockReservationUseCase;
    private final CancelStockReservationUseCase cancelStockReservationUseCase;

    public StockRequestListener(ReserveStockUseCase reserveStockUseCase, EffectiveStockReservationUseCase effectiveStockReservationUseCase, CancelStockReservationUseCase cancelStockReservationUseCase) {
        this.reserveStockUseCase = reserveStockUseCase;
        this.effectiveStockReservationUseCase = effectiveStockReservationUseCase;
        this.cancelStockReservationUseCase = cancelStockReservationUseCase;
    }

    @SqsListener("work-order-stock-requested")
    public void onWorkOrderStockRequested(StockRequestedEvent event) throws BusinessRuleException, NotFoundException {

        var items = event.items().stream()
                .map(i -> new ReservationItem(i.partId(), i.quantity()))
                .collect(Collectors.toList());

        reserveStockUseCase.execute(event.workOrderId(), items);
    }

    @SqsListener("work-order-stock-approved")
    public void onWorkOrderStockApproved(StockApprovedEvent event) throws BusinessRuleException {

        var items = event.items().stream()
                .map(i -> new ReservationItem(i.partId(), i.quantity()))
                .collect(Collectors.toList());

        effectiveStockReservationUseCase.execute(items);
    }

    @SqsListener("work-order-stock-cancel-requested")
    public void onStockCancel(StockCancelRequestedEvent event) throws BusinessRuleException {
        var items = event.items().stream()
                .map(i -> new ReservationItem(i.partId(), i.quantity()))
                .collect(Collectors.toList());

        cancelStockReservationUseCase.execute(items);
    }
}
