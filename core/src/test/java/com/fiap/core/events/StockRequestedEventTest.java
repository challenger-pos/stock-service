package com.fiap.core.events;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StockRequestedEventTest {

    @Test
    void recordInstantiation() {
        UUID id = UUID.randomUUID();
        StockRequestedEvent.Item item = new StockRequestedEvent.Item(UUID.randomUUID(), 5);
        StockRequestedEvent event = new StockRequestedEvent(id, List.of(item));

        assertEquals(id, event.workOrderId());
        assertNotNull(event.items());
        assertEquals(1, event.items().size());
        assertEquals(item, event.items().get(0));
        assertTrue(event.toString().contains("workOrderId"));
    }
}
