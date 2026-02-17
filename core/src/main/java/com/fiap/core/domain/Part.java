package com.fiap.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Part {

    private UUID id;
    private String name;
    private Stock stock;
    private Money price;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}

