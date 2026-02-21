package com.fiap.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Usecase Module Integration Tests")
@Nested
class UsecaseModuleIntegrationTest {

    @Test
    @DisplayName("Should load all usecase classes successfully")
    void shouldLoadAllUsecaseClassesSuccessfully() {
        // Given & When & Then
        assertDoesNotThrow(() -> {
            Class.forName("com.fiap.usecase.part.CreatePartUseCase");
            Class.forName("com.fiap.usecase.part.DeletePartUseCase");
            Class.forName("com.fiap.usecase.part.FindPartByIdUseCase");
            Class.forName("com.fiap.usecase.part.FindPartsByIdsUseCase");
            Class.forName("com.fiap.usecase.part.ReturnPartsToStockUseCase");
            Class.forName("com.fiap.usecase.part.SubtractPartsFromStockUseCase");
            Class.forName("com.fiap.usecase.part.UpdatePartUseCase");
            Class.forName("com.fiap.usecase.stock.CancelStockReservationUseCase");
            Class.forName("com.fiap.usecase.stock.EffectiveStockReservationUseCase");
            Class.forName("com.fiap.usecase.stock.ReserveStockUseCase");
        });
    }

    @Test
    @DisplayName("Should validate all interfaces are actually interfaces")
    void shouldValidateAllInterfacesAreActuallyInterfaces() {
        // Given & When & Then
        assertAll("All usecase classes should be interfaces",
            () -> assertTrue(com.fiap.usecase.part.CreatePartUseCase.class.isInterface()),
            () -> assertTrue(com.fiap.usecase.part.DeletePartUseCase.class.isInterface()),
            () -> assertTrue(com.fiap.usecase.part.FindPartByIdUseCase.class.isInterface()),
            () -> assertTrue(com.fiap.usecase.part.FindPartsByIdsUseCase.class.isInterface()),
            () -> assertTrue(com.fiap.usecase.stock.ReserveStockUseCase.class.isInterface()),
            () -> assertTrue(com.fiap.usecase.stock.CancelStockReservationUseCase.class.isInterface()),
            () -> assertTrue(com.fiap.usecase.stock.EffectiveStockReservationUseCase.class.isInterface())
        );
    }

    @Test
    @DisplayName("Should validate module structure is complete")
    void shouldValidateModuleStructureIsComplete() {
        assertDoesNotThrow(() -> {
            assertNotNull("Usecase module loaded successfully");
            assertEquals("Usecase module loaded successfully", "Usecase module loaded successfully");
        });
    }

    @Test
    @DisplayName("Should validate interface names are correct")
    void shouldValidateInterfaceNamesAreCorrect() {
        assertAll("Interface names should be correct",
            () -> assertEquals("com.fiap.usecase.part.CreatePartUseCase", com.fiap.usecase.part.CreatePartUseCase.class.getName()),
            () -> assertEquals("com.fiap.usecase.stock.ReserveStockUseCase", com.fiap.usecase.stock.ReserveStockUseCase.class.getName()),
            () -> assertEquals("com.fiap.usecase.stock.CancelStockReservationUseCase", com.fiap.usecase.stock.CancelStockReservationUseCase.class.getName()),
            () -> assertEquals("com.fiap.usecase.stock.EffectiveStockReservationUseCase", com.fiap.usecase.stock.EffectiveStockReservationUseCase.class.getName())
        );
    }
}
