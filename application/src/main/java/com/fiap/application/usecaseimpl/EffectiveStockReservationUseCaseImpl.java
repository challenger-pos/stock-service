package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.PartGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.domain.ReservationItem;
import com.fiap.core.domain.Stock;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.usecase.EffectiveStockReservationUseCase;

import java.util.List;
import java.util.UUID;

public class EffectiveStockReservationUseCaseImpl implements EffectiveStockReservationUseCase {

    private final PartGateway partGateway;

    public EffectiveStockReservationUseCaseImpl(PartGateway partGateway) {
        this.partGateway = partGateway;
    }

    @Override
    public void execute(List<ReservationItem> items) throws BusinessRuleException {

        for (ReservationItem item : items) {

            Part part = partGateway.findById(item.partId()).get();

            Stock stock = part.getStock();

            stock.effective(item.quantity());

            partGateway.save(part);
        }
    }
}
