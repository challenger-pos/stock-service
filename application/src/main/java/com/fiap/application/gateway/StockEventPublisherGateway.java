package com.fiap.application.gateway;

import com.fiap.core.events.StockFailedEvent;
import com.fiap.core.events.StockReservedEvent;

public interface StockEventPublisherGateway {

    void publishStockReserved(StockReservedEvent event);
    void publishStockFailed(StockFailedEvent event);
}