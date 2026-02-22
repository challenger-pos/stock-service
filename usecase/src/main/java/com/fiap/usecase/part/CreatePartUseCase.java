package com.fiap.usecase.part;

import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;

public interface CreatePartUseCase {
    Part execute(Part part) throws BusinessRuleException;
}
