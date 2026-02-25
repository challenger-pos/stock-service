package com.fiap.usecase.part;

import com.fiap.core.domain.Part;

import java.util.List;
import java.util.UUID;

public interface FindPartsByIdsUseCase {
    List<Part> execute(List<UUID> ids);
}