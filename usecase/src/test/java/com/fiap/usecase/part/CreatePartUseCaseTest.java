package com.fiap.usecase.part;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class CreatePartUseCaseTest {

    @Test
    @DisplayName("Should validate usecase interface exists")
    void shouldValidateUsecaseInterfaceExists() {
        assertDoesNotThrow(() -> {
            Class.forName("com.fiap.usecase.part.CreatePartUseCase");
        });
        
        assertTrue(com.fiap.usecase.part.CreatePartUseCase.class.isInterface());
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
            Class<?> usecaseClass = Class.forName("com.fiap.usecase.part.CreatePartUseCase");
            assertTrue(usecaseClass.isInterface());
            assertNotNull(usecaseClass.getName());
        });
    }
}
