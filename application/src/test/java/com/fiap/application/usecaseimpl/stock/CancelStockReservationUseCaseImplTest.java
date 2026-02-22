package com.fiap.application.usecaseimpl.stock;

import com.fiap.application.gateway.PartGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.domain.ReservationItem;
import com.fiap.core.domain.Stock;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelStockReservationUseCaseImplTest {

    @Mock
    PartGateway partGateway;

    @Mock
    ReservationItem item;

    @Mock
    Part part;

    @Mock
    Stock stock;

    @Test
    void shouldReleaseStockAndSavePart() throws BusinessRuleException, NotFoundException {
        UUID partId = UUID.randomUUID();

        when(item.partId()).thenReturn(partId);
        when(item.quantity()).thenReturn(5);

        when(partGateway.findById(partId)).thenReturn(part);
        when(part.getStock()).thenReturn(stock);

        CancelStockReservationUseCaseImpl useCase =
                new CancelStockReservationUseCaseImpl(partGateway);

        useCase.execute(List.of(item));

        InOrder inOrder = inOrder(partGateway, part, stock);

        inOrder.verify(partGateway).findById(partId);
        inOrder.verify(part).getStock();
        inOrder.verify(stock).release(5);
        inOrder.verify(partGateway).save(part);

        verifyNoMoreInteractions(partGateway, part, stock);
    }

    @Test
    void shouldThrowExceptionWhenPartNotFound() throws BusinessRuleException, NotFoundException {
        UUID partId = UUID.randomUUID();

        when(item.partId()).thenReturn(partId);
        when(partGateway.findById(partId)).thenThrow(new NotFoundException("Part not found", "PART-404"));

        CancelStockReservationUseCaseImpl useCase =
                new CancelStockReservationUseCaseImpl(partGateway);

        assertThrows(
                NotFoundException.class,
                () -> useCase.execute(List.of(item))
        );
    }

    @Test
    void shouldPropagateExceptionWhenReleaseFails() throws BusinessRuleException, NotFoundException {
        UUID partId = UUID.randomUUID();

        when(item.partId()).thenReturn(partId);
        when(item.quantity()).thenReturn(2);

        when(partGateway.findById(partId)).thenReturn(part);
        when(part.getStock()).thenReturn(stock);

        doThrow(new RuntimeException("Erro na liberação"))
                .when(stock).release(anyInt());

        CancelStockReservationUseCaseImpl useCase =
                new CancelStockReservationUseCaseImpl(partGateway);

        assertThrows(
                RuntimeException.class,
                () -> useCase.execute(List.of(item))
        );
    }
}
