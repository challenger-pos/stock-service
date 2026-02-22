package com.fiap.application.usecaseimpl.stock;

import com.fiap.application.gateway.PartGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.domain.ReservationItem;
import com.fiap.core.domain.Stock;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.stock.EffectiveStockReservationUseCase;

import java.util.List;

public class EffectiveStockReservationUseCaseImpl implements EffectiveStockReservationUseCase {

    private final PartGateway partGateway;

    public EffectiveStockReservationUseCaseImpl(PartGateway partGateway) {
        this.partGateway = partGateway;
    }

    @Override
    public void execute(List<ReservationItem> items) throws BusinessRuleException, NotFoundException {

        for (ReservationItem item : items) {

            Part part = partGateway.findById(item.partId());

            Stock stock = part.getStock();

            stock.effective(item.quantity());

            partGateway.save(part);
        }
    }
}
