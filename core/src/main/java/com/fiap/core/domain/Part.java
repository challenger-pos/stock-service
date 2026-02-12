package com.fiap.core.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Part {

    private UUID id;
    private String name;
    private Stock stock;

    public Part(UUID id, String name, Stock stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Stock getStock() {
        return stock;
    }

}

