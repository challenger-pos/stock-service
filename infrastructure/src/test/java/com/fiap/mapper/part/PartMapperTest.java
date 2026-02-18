package com.fiap.mapper.part;

import com.fiap.core.domain.Money;
import com.fiap.core.domain.Part;
import com.fiap.core.domain.Stock;
import com.fiap.dto.part.CreatePartRequest;
import com.fiap.dto.part.PartResponse;
import com.fiap.dto.part.UpdatePartRequest;
import com.fiap.persistence.entity.PartEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PartMapperTest {

    private final PartMapper mapper = new PartMapper();

    @Test
    void toDomain_fromCreateRequest_shouldMapFields() throws Exception {
        var req = new CreatePartRequest("Widget", BigDecimal.valueOf(12.5), 10);

        Part part = mapper.toDomain(req);

        assertNotNull(part);
        assertEquals("Widget", part.getName());
        assertEquals(0, part.getStock().getReserved());
        assertEquals(10, part.getStock().getQuantity());
        assertEquals(0, part.getPrice().getValue().compareTo(BigDecimal.valueOf(12.5)));
    }

    @Test
    void toDomain_fromUpdateRequest_shouldMapIdAndReserved() throws Exception {
        UUID id = UUID.randomUUID();
        var req = new UpdatePartRequest("Gadget", BigDecimal.valueOf(5.25), 7, 2);

        Part part = mapper.toDomain(id, req);

        assertNotNull(part);
        assertEquals(id, part.getId());
        assertEquals("Gadget", part.getName());
        assertEquals(7, part.getStock().getQuantity());
        assertEquals(2, part.getStock().getReserved());
    }

    @Test
    void toResponse_and_toEntity_and_backFromEntity_shouldPreserveImportantFields() throws Exception {
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        Part part = Part.builder()
                .id(id)
                .name("Thing")
                .price(Money.of(BigDecimal.valueOf(99.99)))
                .stock(Stock.of(20, 3))
                .createdAt(now)
                .updatedAt(now)
                .build();

        PartResponse resp = mapper.toResponse(part);
        assertEquals(id, resp.id());
        assertEquals("Thing", resp.name());
        assertEquals(20, resp.stockQuantity());
        assertEquals(3, resp.reservedStock());

        PartEntity entity = mapper.toEntity(part);
        assertEquals(part.getName(), entity.getName());
        assertEquals(part.getPrice().getValue(), entity.getPrice());

        Part fromEntity = mapper.toDomain(entity);
        assertEquals(entity.getName(), fromEntity.getName());
        assertEquals(entity.getQuantity(), fromEntity.getStock().getQuantity());
        assertEquals(entity.getReserved(), fromEntity.getStock().getReserved());
    }

    @Test
    void toEntity_and_back_additionalCase() throws Exception {
        PartEntity entity = PartEntity.builder()
                .id(UUID.randomUUID())
                .name("p")
                .price(BigDecimal.valueOf(5))
                .quantity(7)
                .reserved(2)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        Part domain = mapper.toDomain(entity);
        assertNotNull(domain);
        assertEquals(entity.getName(), domain.getName());

        var e2 = mapper.toEntity(domain);
        assertEquals(domain.getName(), e2.getName());
        assertEquals(domain.getStock().getQuantity(), e2.getQuantity());
    }
}
