package com.fiap.config;

import com.fiap.application.gateway.PartGateway;
import com.fiap.application.gateway.StockEventPublisherGateway;
import com.fiap.application.usecaseimpl.CancelStockReservationUseCaseImpl;
import com.fiap.application.usecaseimpl.EffectiveStockReservationUseCaseImpl;
import com.fiap.application.usecaseimpl.ReserveStockUseCaseImpl;
import com.fiap.usecase.CancelStockReservationUseCase;
import com.fiap.usecase.EffectiveStockReservationUseCase;
import com.fiap.usecase.ReserveStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockConfig {

    @Bean
    public ReserveStockUseCase reserveStockUseCase(PartGateway partGateway, StockEventPublisherGateway stockEventPublisherGateway) {
        return new ReserveStockUseCaseImpl(partGateway, stockEventPublisherGateway);
    }

    @Bean
    public EffectiveStockReservationUseCase effectiveStockReservationUseCase(PartGateway partGateway) {
        return new EffectiveStockReservationUseCaseImpl(partGateway);
    }

    @Bean
    public CancelStockReservationUseCase cancelStockReservationUseCase(PartGateway partGateway) {
        return new CancelStockReservationUseCaseImpl(partGateway);
    }
}
