package com.fiap.application.gateway;

import com.fiap.core.events.WorkOrderStockRequestedEvent;

import java.util.List;

public interface StockGateway {
    boolean reserve(List<WorkOrderStockRequestedEvent.Item> items);
}