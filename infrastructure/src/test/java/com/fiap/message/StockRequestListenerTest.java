package com.fiap.message;

import com.fiap.core.events.StockApprovedEvent;
import com.fiap.core.events.StockCancelRequestedEvent;
import com.fiap.core.events.StockRequestedEvent;
import com.fiap.usecase.stock.CancelStockReservationUseCase;
import com.fiap.usecase.stock.EffectiveStockReservationUseCase;
import com.fiap.usecase.stock.ReserveStockUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class StockRequestListenerTest {

    private ReserveStockUseCase reserve;
    private EffectiveStockReservationUseCase effective;
    private CancelStockReservationUseCase cancel;
    private StockRequestListener listener;

    @BeforeEach
    void setup() {
        reserve = mock(ReserveStockUseCase.class);
        effective = mock(EffectiveStockReservationUseCase.class);
        cancel = mock(CancelStockReservationUseCase.class);
        listener = new StockRequestListener(reserve, effective, cancel);
    }

    @Test
    void onWorkOrderStockRequested_callsReserveUseCase() throws Exception {
        var workOrder = UUID.randomUUID();
        var item = new StockRequestedEvent.Item(UUID.randomUUID(), 5);
        var event = new StockRequestedEvent(workOrder, List.of(item));

        listener.onWorkOrderStockRequested(event);

        verify(reserve, times(1)).execute(eq(workOrder), anyList());
        verifyNoMoreInteractions(reserve, effective, cancel);
    }

    @Test
    void onWorkOrderStockApproved_callsEffectiveUseCase() throws Exception {
        var workOrder = UUID.randomUUID();
        var item = new StockApprovedEvent.Item(UUID.randomUUID(), 3);
        var event = new StockApprovedEvent(workOrder, List.of(item));

        listener.onWorkOrderStockApproved(event);

        verify(effective, times(1)).execute(anyList());
        verifyNoMoreInteractions(reserve, effective, cancel);
    }

    @Test
    void onStockCancel_callsCancelUseCase() throws Exception {
        var workOrder = UUID.randomUUID();
        var item = new StockCancelRequestedEvent.Item(UUID.randomUUID(), 2);
        var event = new StockCancelRequestedEvent(workOrder, List.of(item));

        listener.onStockCancel(event);

        verify(cancel, times(1)).execute(anyList());
        verifyNoMoreInteractions(reserve, effective, cancel);
    }
}
