package com.fiap.exception;

import com.fiap.core.exception.BadRequestException;
import com.fiap.core.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleDomainException_returnsMappedErrorResponse() {
        BadRequestException ex = new BadRequestException("Nome invalido", "BR001");

        ResponseEntity<ErrorResponse> resp = handler.handleDomainException(ex);

        assertNotNull(resp);
        assertEquals(400, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("BR001", resp.getBody().getCode());
        assertEquals("Nome invalido", resp.getBody().getMessage());
    }

    @Test
    void handleDomainException_forNotFound_returns404() {
        NotFoundException ex = new NotFoundException("msg", "NOT_FOUND");
        ResponseEntity<ErrorResponse> resp = handler.handleDomainException(ex);

        assertNotNull(resp);
        assertEquals(404, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("NOT_FOUND", resp.getBody().getCode());
    }

    @Test
    void handleGenericException_returnsInternalError() {
        RuntimeException ex = new RuntimeException("boom");

        ResponseEntity<ErrorResponse> resp = handler.handleGenericException(ex);

        assertNotNull(resp);
        assertEquals(500, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("INTERNAL_ERROR", resp.getBody().getCode());
        assertEquals("boom", resp.getBody().getMessage());
    }
}
