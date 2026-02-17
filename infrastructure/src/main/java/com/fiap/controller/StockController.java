package com.fiap.controller;

import com.fiap.core.domain.ReservationItem;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.stock.StockCancelReservationRequest;
import com.fiap.dto.stock.StockEffectiveReservationRequest;
import com.fiap.dto.stock.StockReservationRequest;
import com.fiap.usecase.stock.CancelStockReservationUseCase;
import com.fiap.usecase.stock.EffectiveStockReservationUseCase;
import com.fiap.usecase.stock.ReserveStockUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final ReserveStockUseCase reserveStockUseCase;
    private final EffectiveStockReservationUseCase effectiveStockReservationUseCase;
    private final CancelStockReservationUseCase cancelStockReservationUseCase;

    public StockController(ReserveStockUseCase reserveStockUseCase, EffectiveStockReservationUseCase effectiveStockReservationUseCase, CancelStockReservationUseCase cancelStockReservationUseCase) {
        this.reserveStockUseCase = reserveStockUseCase;
        this.effectiveStockReservationUseCase = effectiveStockReservationUseCase;
        this.cancelStockReservationUseCase = cancelStockReservationUseCase;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserve(@RequestBody StockReservationRequest request) throws BusinessRuleException, NotFoundException {
        var items = request.items().stream()
                .map(i -> new ReservationItem(i.partId(), i.quantity()))
                .toList();

        reserveStockUseCase.execute(request.workOrderId(), items);

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/approveReserve")
    public ResponseEntity<Void> reserveApproved(@RequestBody StockEffectiveReservationRequest request) throws BusinessRuleException, NotFoundException {
        var items = request.items().stream()
                .map(i -> new ReservationItem(i.partId(), i.quantity()))
                .toList();

        effectiveStockReservationUseCase.execute(items);

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/cancelReserve")
    public ResponseEntity<Void> cancelReserve(@RequestBody StockCancelReservationRequest request) throws BusinessRuleException, NotFoundException {
        var items = request.items().stream()
                .map(i -> new ReservationItem(i.partId(), i.quantity()))
                .toList();

        cancelStockReservationUseCase.execute(items);

        return ResponseEntity.accepted().build();
    }
}

