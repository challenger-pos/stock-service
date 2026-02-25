package com.fiap.application.usecaseimpl.part;

import com.fiap.application.gateway.PartGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.usecase.part.CreatePartUseCase;

public class CreatePartUseCaseImpl implements CreatePartUseCase {

    private final PartGateway partGateway;

    public CreatePartUseCaseImpl(PartGateway partGateway) {
        this.partGateway = partGateway;
    }

    @Override
    public Part execute(Part part) throws BusinessRuleException {
        return partGateway.create(part);
    }
}