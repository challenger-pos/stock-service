package com.fiap.gateway;

import com.fiap.application.gateway.StockEventPublisherGateway;
import com.fiap.core.events.StockFailedEvent;
import com.fiap.core.events.StockReservedEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockEventPublisherGatewayImpl implements StockEventPublisherGateway {

    private final SqsTemplate template;

    public StockEventPublisherGatewayImpl(SqsTemplate template) {
        this.template = template;
    }

    @Override
    public void publishStockReserved(StockReservedEvent event) {
        template.send("stock-reserved-queue", event);
    }

    @Override
    public void publishStockFailed(StockFailedEvent event) {
        template.send("stock-failed-queue", event);
    }
}
