package com.fiap.dto.part;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PartResponse(
        UUID id,
        String name,
        BigDecimal price,
        Integer stockQuantity,
        Integer reservedStock,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}