package com.example.PinSoul.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static final Set<String> VALID_CATEGORIES = Set.of("USER", "ADMIN");

    private final SecretKey signingKey;
    private final long expirationMillis;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms:86400000}") long expirationMillis) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(String username, String category) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("category", category);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        return validateToken(token).isPresent();
    }

    public Optional<TokenIdentity> validateToken(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }

        try {
            Claims claims = parseClaims(token);
            String username = claims.getSubject();
            String category = claims.get("category", String.class);
            if (username == null || username.isBlank() || category == null || !VALID_CATEGORIES.contains(category)) {
                return Optional.empty();
            }
            return Optional.of(new TokenIdentity(username, category));
        } catch (JwtException | IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    public record TokenIdentity(String username, String category) {
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
