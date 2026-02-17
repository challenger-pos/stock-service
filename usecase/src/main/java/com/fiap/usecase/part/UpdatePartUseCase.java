package com.fiap.usecase.part;

import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;

public interface UpdatePartUseCase {
    Part execute(Part part) throws NotFoundException, BusinessRuleException;
}