package com.fiap.config;

import com.fiap.application.gateway.PartGateway;
import com.fiap.application.usecaseimpl.ReserveStockUseCaseImpl;
import com.fiap.usecase.ReserveStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockConfig {

    @Bean
    public ReserveStockUseCase reserveStockUseCase(PartGateway partGateway) {
        return new ReserveStockUseCaseImpl(partGateway);
    }
}
