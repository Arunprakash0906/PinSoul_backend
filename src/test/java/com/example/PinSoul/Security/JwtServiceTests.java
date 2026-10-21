package com.example.PinSoul.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

class JwtServiceTests {
    private static final String SECRET = "pinsoul-test-secret-key-with-at-least-32-bytes";

    private final JwtService jwtService = new JwtService(SECRET, 60_000);

    @BeforeEach
    void clearSecurityContextBeforeTest() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void clearSecurityContextAfterTest() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void validatesGeneratedTokenAndReturnsItsIdentity() {
        String token = jwtService.generateToken("test-user", "USER");

        assertTrue(jwtService.isTokenValid(token));
        assertEquals(
                new JwtService.TokenIdentity("test-user", "USER"),
                jwtService.validateToken(token).orElseThrow());
    }

    @Test
    void rejectsMissingMalformedAndTamperedTokens() {
        String token = jwtService.generateToken("test-user", "USER");
        String tamperedToken = token.substring(0, token.lastIndexOf('.') + 1) + "invalid-signature";

        assertFalse(jwtService.isTokenValid(null));
        assertFalse(jwtService.isTokenValid(" "));
        assertFalse(jwtService.isTokenValid("not-a-jwt"));
        assertFalse(jwtService.isTokenValid(tamperedToken));
    }

    @Test
    void rejectsExpiredTokens() {
        JwtService expiredTokenService = new JwtService(SECRET, -1);
        String token = expiredTokenService.generateToken("test-user", "USER");

        assertFalse(expiredTokenService.isTokenValid(token));
    }

    @Test
    void rejectsTokensWithUnsupportedCategories() {
        String token = jwtService.generateToken("test-user", "UNKNOWN");
        String missingUsernameToken = jwtService.generateToken(" ", "USER");

        assertFalse(jwtService.isTokenValid(token));
        assertFalse(jwtService.isTokenValid(missingUsernameToken));
    }

    @Test
    void filterRejectsInvalidBearerTokenWithoutContinuing() throws IOException, jakarta.servlet.ServletException {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean();

        filter.doFilter(request, response, (servletRequest, servletResponse) -> chainCalled.set(true));

        assertEquals(401, response.getStatus());
        assertFalse(chainCalled.get());
    }

    @Test
    void filterAuthenticatesValidBearerToken() throws IOException, jakarta.servlet.ServletException {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtService.generateToken("test-user", "USER"));
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean();

        filter.doFilter(request, response, (servletRequest, servletResponse) -> {
            assertEquals("test-user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            chainCalled.set(true);
        });

        assertTrue(chainCalled.get());
        assertEquals(200, response.getStatus());
    }
}
