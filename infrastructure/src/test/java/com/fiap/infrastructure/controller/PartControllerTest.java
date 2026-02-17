package com.fiap.infrastructure.controller;

import com.fiap.controller.PartController;
import com.fiap.core.domain.Money;
import com.fiap.core.domain.Part;
import com.fiap.core.domain.Stock;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.dto.part.CreatePartRequest;
import com.fiap.dto.part.PartResponse;
import com.fiap.dto.part.UpdatePartRequest;
import com.fiap.mapper.part.PartMapper;
import com.fiap.security.jwt.TokenService;
import com.fiap.usecase.part.CreatePartUseCase;
import com.fiap.usecase.part.DeletePartUseCase;
import com.fiap.usecase.part.FindPartByIdUseCase;
import com.fiap.usecase.part.UpdatePartUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartController.class)
@AutoConfigureMockMvc(addFilters = false)
class PartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreatePartUseCase createPartUseCase;

    @MockBean
    private FindPartByIdUseCase findPartByIdUseCase;

    @MockBean
    private UpdatePartUseCase updatePartUseCase;

    @MockBean
    private DeletePartUseCase deletePartUseCase;

    @MockBean
    private PartMapper partMapper;

    @MockBean
    private TokenService tokenService;


    private UUID partId = UUID.randomUUID();

    private Part mockPart() throws BusinessRuleException {
        return Part.builder()
                .id(partId)
                .name("Filtro de Óleo")
                .price(Money.of(BigDecimal.valueOf(50.0)))
                .stock(Stock.of(10, 2))
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
    }

    private PartResponse mockResponse() {
        return new PartResponse(
                partId, "Filtro de Óleo",
                BigDecimal.valueOf(50.0),
                10, 2,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }

    @Test
    void shouldCreatePart() throws Exception {

        CreatePartRequest request = new CreatePartRequest("Filtro de Óleo", BigDecimal.valueOf(50), 10);

        Mockito.when(partMapper.toDomain(any(CreatePartRequest.class))).thenReturn(mockPart());
        Mockito.when(createPartUseCase.execute(any(Part.class))).thenReturn(mockPart());
        Mockito.when(partMapper.toResponse(any(Part.class))).thenReturn(mockResponse());

        mockMvc.perform(
                        post("/stock/parts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(partId.toString()))
                .andExpect(jsonPath("$.name").value("Filtro de Óleo"));
    }

    @Test
    void shouldReturnPartById() throws Exception {

        Mockito.when(findPartByIdUseCase.execute(eq(partId))).thenReturn(mockPart());
        Mockito.when(partMapper.toResponse(any(Part.class))).thenReturn(mockResponse());

        mockMvc.perform(
                        get("/stock/parts/" + partId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(partId.toString()))
                .andExpect(jsonPath("$.name").value("Filtro de Óleo"));
    }

    @Test
    void shouldUpdatePart() throws Exception {

        UpdatePartRequest request = new UpdatePartRequest("Filtro de Óleo", BigDecimal.valueOf(100), 20, 5);

        Mockito.when(partMapper.toDomain(eq(partId), any(UpdatePartRequest.class))).thenReturn(mockPart());
        Mockito.when(updatePartUseCase.execute(any(Part.class))).thenReturn(mockPart());
        Mockito.when(partMapper.toResponse(any(Part.class))).thenReturn(mockResponse());

        mockMvc.perform(
                        put("/stock/parts/" + partId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(partId.toString()));
    }

    @Test
    void shouldDeletePart() throws Exception {

        Mockito.doNothing().when(deletePartUseCase).execute(eq(partId));

        mockMvc.perform(
                        delete("/stock/parts/" + partId)
                )
                .andExpect(status().isNoContent());
    }
}
