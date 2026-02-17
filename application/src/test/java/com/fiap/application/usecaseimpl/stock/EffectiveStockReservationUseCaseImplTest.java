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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EffectiveStockReservationUseCaseImplTest {

    @Mock
    PartGateway partGateway;

    @Mock
    ReservationItem item;

    @Mock
    Part part;

    @Mock
    Stock stock;

    @Test
    void shouldApplyEffectiveReservationAndSavePart() throws BusinessRuleException, NotFoundException {
        UUID partId = UUID.randomUUID();

        when(item.partId()).thenReturn(partId);
        when(item.quantity()).thenReturn(4);

        when(partGateway.findById(partId)).thenReturn(Optional.of(part));
        when(part.getStock()).thenReturn(stock);

        EffectiveStockReservationUseCaseImpl useCase =
                new EffectiveStockReservationUseCaseImpl(partGateway);

        useCase.execute(List.of(item));

        InOrder inOrder = inOrder(partGateway, part, stock);

        inOrder.verify(partGateway).findById(partId);
        inOrder.verify(part).getStock();
        inOrder.verify(stock).effective(4);
        inOrder.verify(partGateway).save(part);

        verifyNoMoreInteractions(partGateway, part, stock);
    }

    @Test
    void shouldThrowExceptionWhenPartNotFound() throws BusinessRuleException, NotFoundException {
        UUID partId = UUID.randomUUID();

        when(item.partId()).thenReturn(partId);
        when(partGateway.findById(partId)).thenReturn(Optional.empty());

        EffectiveStockReservationUseCaseImpl useCase =
                new EffectiveStockReservationUseCaseImpl(partGateway);

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> useCase.execute(List.of(item))
        );
    }

    @Test
    void shouldPropagateExceptionWhenEffectiveFails() throws BusinessRuleException, NotFoundException {
        UUID partId = UUID.randomUUID();

        when(item.partId()).thenReturn(partId);
        when(item.quantity()).thenReturn(2);

        when(partGateway.findById(partId)).thenReturn(Optional.of(part));
        when(part.getStock()).thenReturn(stock);

        doThrow(new RuntimeException("Falha"))
                .when(stock).effective(anyInt());

        EffectiveStockReservationUseCaseImpl useCase =
                new EffectiveStockReservationUseCaseImpl(partGateway);

        assertThrows(
                RuntimeException.class,
                () -> useCase.execute(List.of(item))
        );
    }
}
