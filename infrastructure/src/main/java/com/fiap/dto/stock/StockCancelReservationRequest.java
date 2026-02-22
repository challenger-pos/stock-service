package com.fiap.dto.stock;

import java.util.List;
import java.util.UUID;

public record StockCancelReservationRequest(
        List<Item> items
) {
    public record Item(
            UUID partId,
            int quantity
    ) {}
}