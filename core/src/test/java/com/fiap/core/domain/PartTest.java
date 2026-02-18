package com.fiap.core.domain;

import org.junit.jupiter.api.Test;
import com.fiap.core.exception.BusinessRuleException;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    @Test
    void builderAndAccessors() throws BusinessRuleException {
        UUID id = UUID.randomUUID();
        Stock stock = Stock.of(10, 2);
        Money price = Money.of(java.math.BigDecimal.valueOf(19.99));
        OffsetDateTime now = OffsetDateTime.now();

        Part p = Part.builder()
                .id(id)
                .name("Part A")
                .stock(stock)
                .price(price)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(id, p.getId());
        assertEquals("Part A", p.getName());
        assertEquals(stock, p.getStock());
        assertEquals(price, p.getPrice());
        assertEquals(now, p.getCreatedAt());
        assertEquals(now, p.getUpdatedAt());

        // setters
        Part p2 = new Part();
        p2.setId(id);
        p2.setName("Part A");
        p2.setStock(stock);
        p2.setPrice(price);
        p2.setCreatedAt(now);
        p2.setUpdatedAt(now);

        assertEquals(p, p2);
        assertEquals(p.hashCode(), p2.hashCode());
        assertTrue(p.toString().contains("Part A"));
    }
}
