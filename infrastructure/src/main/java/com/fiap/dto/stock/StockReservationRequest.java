package com.fiap.dto.stock;

import java.util.List;
import java.util.UUID;

public record StockReservationRequest(
        UUID workOrderId,
        List<Item> items
) {
    public record Item(
            UUID partId,
            int quantity
    ) {}
}