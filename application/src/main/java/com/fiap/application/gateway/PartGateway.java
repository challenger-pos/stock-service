package com.fiap.application.gateway;

import com.fiap.core.domain.Part;

import java.util.Optional;
import java.util.UUID;

public interface PartGateway {
    Optional<Part> findById(UUID id);
    void save(Part part);
}

