package com.fiap.persistence.entity;

import com.fiap.core.domain.Part;
import com.fiap.core.domain.Stock;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "parts")
public class PartEntity {

    @Id
    private UUID id;

    private String name;
    private int quantity;
    private int reserved;

    public Part toDomain() {
        return new Part(
                id,
                name,
                new Stock(quantity, reserved)
        );
    }

    public static PartEntity fromDomain(Part part) {
        PartEntity e = new PartEntity();
        e.id = part.getId();
        e.name = part.getName();
        e.quantity = part.getStock().getQuantity();
        e.reserved = part.getStock().getReserved();
        return e;
    }
}
