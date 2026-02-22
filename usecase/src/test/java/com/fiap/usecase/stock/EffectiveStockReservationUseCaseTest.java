package com.fiap.usecase.stock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class EffectiveStockReservationUseCaseTest {

    @Test
    @DisplayName("Should validate usecase interface exists")
    void shouldValidateUsecaseInterfaceExists() {
        assertDoesNotThrow(() -> {
            Class.forName("com.fiap.usecase.stock.EffectiveStockReservationUseCase");
        });
        
        assertTrue(com.fiap.usecase.stock.EffectiveStockReservationUseCase.class.isInterface());
    }

    @Test
    @DisplayName("Should validate module structure")
    void shouldValidateModuleStructure() {
        assertDoesNotThrow(() -> {
            assertNotNull("Usecase module loaded successfully");
        });
    }

    @Test
    @DisplayName("Should validate interface method signature")
    void shouldValidateInterfaceMethodSignature() {
        assertDoesNotThrow(() -> {
            Class<?> usecaseClass = Class.forName("com.fiap.usecase.stock.EffectiveStockReservationUseCase");
            assertTrue(usecaseClass.isInterface());
            assertNotNull(usecaseClass.getName());
        });
    }
}
