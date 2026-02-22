package com.fiap.config;

import com.fiap.application.gateway.PartGateway;
import com.fiap.application.usecaseimpl.part.CreatePartUseCaseImpl;
import com.fiap.application.usecaseimpl.part.DeletePartUseCaseImpl;
import com.fiap.application.usecaseimpl.part.FindPartByIdUseCaseImpl;
import com.fiap.application.usecaseimpl.part.UpdatePartUseCaseImpl;
import com.fiap.usecase.part.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PartConfig {

    @Bean
    public CreatePartUseCase createPartUseCase(PartGateway partGateway) {
        return new CreatePartUseCaseImpl(partGateway);
    }

    @Bean
    public DeletePartUseCase deletePartUseCase(PartGateway partGateway) {
        return new DeletePartUseCaseImpl(partGateway);
    }

    @Bean
    public FindPartByIdUseCase findPartByIdUseCase(PartGateway partGateway) {
        return new FindPartByIdUseCaseImpl(partGateway);
    }

    @Bean
    public UpdatePartUseCase updatePartUseCase(PartGateway partGateway, FindPartByIdUseCase findPartByIdUseCase) {
        return new UpdatePartUseCaseImpl(partGateway, findPartByIdUseCase);
    }
}