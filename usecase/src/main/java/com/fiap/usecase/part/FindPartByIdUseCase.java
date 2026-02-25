package com.fiap.usecase.part;

import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;

import java.util.UUID;

public interface FindPartByIdUseCase {
    Part execute(UUID id) throws NotFoundException, BusinessRuleException;
}