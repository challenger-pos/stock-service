package com.fiap.gateway;

import com.fiap.core.events.StockFailedEvent;
import com.fiap.core.events.StockReservedEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StockEventPublisherGatewayImplTest {

    private SqsTemplate sqsTemplate;

    private StockEventPublisherGatewayImpl gateway;

    @BeforeEach
    void setup() {
        sqsTemplate = mock(SqsTemplate.class);
        gateway = new StockEventPublisherGatewayImpl(sqsTemplate);

        ReflectionTestUtils.setField(gateway, "stockReservedQueue", "test-stock-reserved");
        ReflectionTestUtils.setField(gateway, "stockFailedQueue", "test-stock-failed");
    }

    @Test
    void shouldPublishStockReservedEventToCorrectQueue() {
        UUID workOrderId = UUID.randomUUID();
        StockReservedEvent event = new StockReservedEvent(workOrderId);

        gateway.publishStockReserved(event);

        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);

        verify(sqsTemplate, times(1)).send(eq("test-stock-reserved"), eventCaptor.capture());

        assertEquals(event, eventCaptor.getValue());
    }

    @Test
    void shouldPublishStockFailedEventToCorrectQueue() {
        UUID workOrderId = UUID.randomUUID();
        String reason = "Insufficient stock";

        StockFailedEvent event = new StockFailedEvent(workOrderId, reason);

        gateway.publishStockFailed(event);

        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);

        verify(sqsTemplate, times(1)).send(eq("test-stock-failed"), eventCaptor.capture());

        assertEquals(event, eventCaptor.getValue());
    }
}
