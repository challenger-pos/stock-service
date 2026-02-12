package com.fiap.usecase;

import java.util.UUID;

public interface ReleaseStockUseCase {
    void execute(UUID partId, int quantity);
}

