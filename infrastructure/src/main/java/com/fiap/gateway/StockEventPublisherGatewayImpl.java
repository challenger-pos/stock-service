package com.fiap.gateway;

import com.fiap.application.gateway.StockEventPublisherGateway;
import com.fiap.core.events.StockFailedEvent;
import com.fiap.core.events.StockReservedEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "aws.sqs.enabled", havingValue = "true", matchIfMissing = true)
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
        template.send(stockReservedQueue, event);
    }

    @Override
    public void publishStockFailed(StockFailedEvent event) {
        template.send(stockFailedQueue, event);
    }
}
