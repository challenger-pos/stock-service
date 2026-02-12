package com.fiap.message;

import com.fiap.core.events.WorkOrderStockRequestedEvent;
import com.fiap.usecase.ProcessStockRequestUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class StockRequestListener {

    /*private final ProcessStockRequestUseCase processStockRequestUseCase;

    public StockRequestListener(ProcessStockRequestUseCase processStockRequestUseCase) {
        this.processStockRequestUseCase = processStockRequestUseCase;
    }

    @SqsListener("work-order-stock-requested")
    public void onStockRequested(WorkOrderStockRequestedEvent event) {
        processStockRequestUseCase.execute(event);
    }*/
}
