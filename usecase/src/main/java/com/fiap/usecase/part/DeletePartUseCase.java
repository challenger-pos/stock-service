package com.fiap.usecase.part;

import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;

import java.util.UUID;

public interface DeletePartUseCase {
    void execute(UUID id) throws NotFoundException, BusinessRuleException;
}