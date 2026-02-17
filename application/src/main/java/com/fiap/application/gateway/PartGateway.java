package com.fiap.application.gateway;

import com.fiap.core.domain.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartGateway {
    Optional<Part> findById(UUID id) throws NotFoundException, BusinessRuleException;
    void save(Part part);
    Part create(Part part) throws BusinessRuleException;
    void saveAll(List<Part> parts);
    Part update(Part part) throws BusinessRuleException;
    void delete(UUID id);
    boolean existsById(UUID id);
}

