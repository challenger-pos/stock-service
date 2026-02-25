package com.fiap.core.events;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EventsRecordTest {

    @Test
    void stockRequestedEvent_hasValues() {
        UUID id = UUID.randomUUID();
        StockRequestedEvent.Item item = new StockRequestedEvent.Item(UUID.randomUUID(), 5);
        StockRequestedEvent evt = new StockRequestedEvent(id, List.of(item));

        assertEquals(id, evt.workOrderId());
        assertNotNull(evt.items());
        assertEquals(1, evt.items().size());
        assertEquals(item.partId(), evt.items().get(0).partId());
        assertEquals(5, evt.items().get(0).quantity());
    }

    @Test
    void stockReservedEvent_hasWorkOrderId() {
        UUID id = UUID.randomUUID();
        StockReservedEvent evt = new StockReservedEvent(id);
        assertEquals(id, evt.workOrderId());
    }

    @Test
    void stockFailedEvent_hasFields() {
        UUID id = UUID.randomUUID();
        String reason = "no stock";
        StockFailedEvent evt = new StockFailedEvent(id, reason);
        assertEquals(id, evt.workOrderId());
        assertEquals(reason, evt.reason());
    }

    @Test
    void stockApprovedEvent_items() {
        UUID id = UUID.randomUUID();
        StockApprovedEvent.Item item = new StockApprovedEvent.Item(UUID.randomUUID(), 2);
        StockApprovedEvent evt = new StockApprovedEvent(id, List.of(item));

        assertEquals(1, evt.items().size());
        assertEquals(2, evt.items().get(0).quantity());
    }

    @Test
    void stockCancelRequestedEvent_items() {
        UUID id = UUID.randomUUID();
        StockCancelRequestedEvent.Item item = new StockCancelRequestedEvent.Item(UUID.randomUUID(), 3);
        StockCancelRequestedEvent evt = new StockCancelRequestedEvent(id, List.of(item));

        assertEquals(id, evt.workOrderId());
        assertEquals(3, evt.items().get(0).quantity());
    }
}
