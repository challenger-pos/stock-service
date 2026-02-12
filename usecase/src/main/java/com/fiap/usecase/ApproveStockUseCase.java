package com.fiap.usecase;

import java.util.UUID;

public interface ApproveStockUseCase {
    void execute(UUID partId, int quantity);
}

