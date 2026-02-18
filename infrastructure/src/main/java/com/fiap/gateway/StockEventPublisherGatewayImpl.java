package com.fiap.gateway;

import com.fiap.application.gateway.StockEventPublisherGateway;
import com.fiap.core.events.StockFailedEvent;
import com.fiap.core.events.StockReservedEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockEventPublisherGatewayImpl implements StockEventPublisherGateway {

    private final SqsTemplate template;

    @Value("${sqs.queue.stock-reserved:stock-reserved-queue}")
    private String stockReservedQueue;

    @Value("${sqs.queue.stock-failed:stock-failed-queue}")
    private String stockFailedQueue;

    public StockEventPublisherGatewayImpl(SqsTemplate template) {
        this.template = template;
    }

    @Override
    public void publishStockReserved(StockReservedEvent event) {
        log.info("Received StockReservedEvent: {}", event);
        template.send(stockReservedQueue, event);
    }

    @Override
    public void publishStockFailed(StockFailedEvent event) {
        log.info("Received StockFailedEvent: {}", event);
        template.send(stockFailedQueue, event);
    }
}
