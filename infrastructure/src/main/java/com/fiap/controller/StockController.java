package com.fiap.controller;

import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.ReserveRequest;
import com.fiap.usecase.ReserveStockUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final ReserveStockUseCase reserve;

    public StockController(ReserveStockUseCase reserve) {
        this.reserve = reserve;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserve(@RequestBody ReserveRequest req) throws BusinessRuleException, NotFoundException {
        reserve.execute(req.partId(), req.quantity());
        return ResponseEntity.ok().build();
    }
}

