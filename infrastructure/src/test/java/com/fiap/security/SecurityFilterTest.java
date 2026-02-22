package com.fiap.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fiap.security.jwt.TokenService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityFilterTest {

    @Test
    void doFilter_withValidToken_setsSecurityContext() throws ServletException, IOException {
        TokenService tokenService = mock(TokenService.class);
        DecodedJWT decoded = mock(DecodedJWT.class);
        when(tokenService.decode("valid-token")).thenReturn(decoded);
        when(decoded.getSubject()).thenReturn("user@example.com");
        Claim claim = mock(Claim.class);
        when(decoded.getClaim("role")).thenReturn(claim);
        when(claim.asString()).thenReturn("ADMIN");

        SecurityFilter filter = new SecurityFilter(tokenService);

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer valid-token");
        MockHttpServletResponse resp = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, resp, chain);

        assertEquals(200, resp.getStatus());
        verify(tokenService).decode("valid-token");
    }

    @Test
    void doFilter_withInvalidToken_returnsUnauthorized() throws ServletException, IOException {
        TokenService tokenService = mock(TokenService.class);
        when(tokenService.decode("bad-token")).thenReturn(null);

        SecurityFilter filter = new SecurityFilter(tokenService);

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer bad-token");
        MockHttpServletResponse resp = new MockHttpServletResponse();
        MockFilterChain chain = mock(MockFilterChain.class);

        filter.doFilter(req, resp, chain);

        assertEquals(401, resp.getStatus());
        assertEquals("Token inválido", resp.getContentAsString());
        verify(tokenService).decode("bad-token");
        verifyNoInteractions(chain);
    }
}
