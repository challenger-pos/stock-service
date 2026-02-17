package com.fiap.application.usecaseimpl.part;

import com.fiap.application.gateway.PartGateway;
import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.part.FindPartByIdUseCase;

import java.util.UUID;

public class FindPartByIdUseCaseImpl implements FindPartByIdUseCase {

    private final PartGateway partGateway;

    public FindPartByIdUseCaseImpl(PartGateway partGateway) {
        this.partGateway = partGateway;
    }

    @Override
    public Part execute(UUID id) throws NotFoundException, BusinessRuleException {
        return partGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Part com ID " + id + " não encontrada.", "PART-404"));
    }
}