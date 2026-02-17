package com.fiap.application.usecaseimpl.stock;

import com.fiap.application.gateway.PartGateway;
import com.fiap.application.gateway.StockEventPublisherGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.domain.ReservationItem;
import com.fiap.core.domain.Stock;
import com.fiap.core.events.StockFailedEvent;
import com.fiap.core.events.StockReservedEvent;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.stock.ReserveStockUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveStockUseCaseImplTest {

    @Mock
    PartGateway partGateway;

    @Mock
    StockEventPublisherGateway publisher;

    @Mock
    Part part;

    @Mock
    Stock stock;

    @Mock
    ReservationItem item;

    @Test
    void shouldReserveStockAndPublishSuccessEvent() throws BusinessRuleException, NotFoundException {
        UUID workOrderId = UUID.randomUUID();

        when(item.partId()).thenReturn(UUID.randomUUID());
        when(item.quantity()).thenReturn(3);

        when(partGateway.findById(item.partId())).thenReturn(Optional.of(part));
        when(part.getStock()).thenReturn(stock);

        ReserveStockUseCase useCase = new ReserveStockUseCaseImpl(partGateway, publisher);

        useCase.execute(workOrderId, List.of(item));

        InOrder inOrder = inOrder(partGateway, publisher, stock);

        inOrder.verify(partGateway).findById(item.partId());
        inOrder.verify(stock).reserve(item.quantity());

        inOrder.verify(partGateway).findById(item.partId());
        inOrder.verify(stock).reserve(item.quantity());
        inOrder.verify(partGateway).save(part);

        inOrder.verify(publisher).publishStockReserved(any(StockReservedEvent.class));

        verifyNoMoreInteractions(partGateway, publisher, stock);
    }

    @Test
    void shouldPublishFailedEventWhenPartNotFound() throws BusinessRuleException, NotFoundException {
        UUID workOrderId = UUID.randomUUID();

        when(item.partId()).thenReturn(UUID.randomUUID());
        when(partGateway.findById(item.partId()))
                .thenReturn(Optional.empty());

        ReserveStockUseCase useCase = new ReserveStockUseCaseImpl(partGateway, publisher);

        useCase.execute(workOrderId, List.of(item));

        verify(publisher).publishStockFailed(any(StockFailedEvent.class));
        verifyNoMoreInteractions(publisher);
    }

    @Test
    void shouldPublishFailedEventWhenExceptionOccurs() throws BusinessRuleException, NotFoundException {
        UUID workOrderId = UUID.randomUUID();

        when(item.partId()).thenReturn(UUID.randomUUID());
        when(item.quantity()).thenReturn(2);

        when(partGateway.findById(item.partId())).thenReturn(Optional.of(part));
        when(part.getStock()).thenReturn(stock);

        doThrow(new RuntimeException("boom")).when(stock).reserve(anyInt());

        ReserveStockUseCase useCase = new ReserveStockUseCaseImpl(partGateway, publisher);

        useCase.execute(workOrderId, List.of(item));

        verify(publisher).publishStockFailed(any(StockFailedEvent.class));
    }
}
