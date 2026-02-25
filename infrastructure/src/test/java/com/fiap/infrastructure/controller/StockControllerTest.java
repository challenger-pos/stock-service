package com.fiap.infrastructure.controller;

import com.fiap.controller.StockController;
import com.fiap.security.jwt.TokenService;
import com.fiap.usecase.stock.CancelStockReservationUseCase;
import com.fiap.usecase.stock.EffectiveStockReservationUseCase;
import com.fiap.usecase.stock.ReserveStockUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StockController.class)
@AutoConfigureMockMvc(addFilters = false)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReserveStockUseCase reserveStockUseCase;

    @MockBean
    private EffectiveStockReservationUseCase effectiveStockReservationUseCase;

    @MockBean
    private CancelStockReservationUseCase cancelStockReservationUseCase;

    @MockBean
    private TokenService tokenService;

    @Test
    void shouldReserveStock() throws Exception {
        UUID woId = UUID.randomUUID();
        UUID part1 = UUID.randomUUID();

        String json = """
            {
              "workOrderId": "%s",
              "items": [
                {"partId": "%s", "quantity": 2}
              ]
            }
            """.formatted(woId, part1);

        mockMvc.perform(post("/stock/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAccepted());

        Mockito.verify(reserveStockUseCase)
                .execute(eq(woId), any());
    }

    @Test
    void shouldApproveReservation() throws Exception {
        UUID part1 = UUID.randomUUID();

        String json = """
            {
              "items": [
                {"partId": "%s", "quantity": 5}
              ]
            }
            """.formatted(part1);

        mockMvc.perform(post("/stock/approve-reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAccepted());

        Mockito.verify(effectiveStockReservationUseCase)
                .execute(any(List.class));
    }

    @Test
    void shouldCancelReservation() throws Exception {
        UUID part1 = UUID.randomUUID();

        String json = """
            {
              "items": [
                {"partId": "%s", "quantity": 1}
              ]
            }
            """.formatted(part1);

        mockMvc.perform(post("/stock/cancel-reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAccepted());

        Mockito.verify(cancelStockReservationUseCase)
                .execute(any(List.class));
    }
}
