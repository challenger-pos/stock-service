package com.fiap.dto;

import java.util.List;
import java.util.UUID;

public record StockEffectiveReservationRequest(
        List<Item> items
) {
    public record Item(
            UUID partId,
            int quantity
    ) {}
}