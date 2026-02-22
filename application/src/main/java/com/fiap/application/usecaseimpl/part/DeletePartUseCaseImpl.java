package com.fiap.application.usecaseimpl.part;


import com.fiap.application.gateway.PartGateway;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.part.DeletePartUseCase;

import java.util.UUID;

public class DeletePartUseCaseImpl implements DeletePartUseCase {

    private final PartGateway partGateway;

    public DeletePartUseCaseImpl(PartGateway partGateway) {
        this.partGateway = partGateway;
    }

    @Override
    public void execute(UUID id) throws NotFoundException, BusinessRuleException {
        if (!partGateway.existsById(id)) {
            throw new NotFoundException("Part with ID " + id + " not found.", "PART-404");
        }

        partGateway.delete(id);
    }
}