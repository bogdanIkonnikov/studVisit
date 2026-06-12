package krefature.studvisit.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtTokenService {
    private final SecretKey accessKey;
    private final SecretKey refreshKey;
    private final long accessExpirationMs;
    private final long refreshExpirationMs;

    public JwtTokenService(
            @Value("${app.security.jwt.access-secret}") String accessSecret,
            @Value("${app.security.jwt.refresh-secret}") String refreshSecret,
            @Value("${app.security.jwt.access-expiration-ms}") long accessExpirationMs,
            @Value("${app.security.jwt.refresh-expiration-ms}") long refreshExpirationMs
    ) {
        this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationMs = accessExpirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public String generateAccessToken(AppUserPrincipal principal) {
        return generateToken(principal, JwtTokenType.ACCESS, accessExpirationMs, accessKey);
    }

    public String generateRefreshToken(AppUserPrincipal principal) {
        return generateToken(principal, JwtTokenType.REFRESH, refreshExpirationMs, refreshKey);
    }

    public long getAccessExpirationMs() {
        return accessExpirationMs;
    }

    public long getRefreshExpirationMs() {
        return refreshExpirationMs;
    }

    public String extractUsername(String token, JwtTokenType type) {
        return extractClaims(token, type).getSubject();
    }

    public JwtTokenType extractTokenType(String token, JwtTokenType expectedType) {
        String value = extractClaims(token, expectedType).get("tokenType", String.class);
        return JwtTokenType.valueOf(value);
    }

    public boolean isTokenValid(String token, AppUserPrincipal principal, JwtTokenType type) {
        Claims claims = extractClaims(token, type);
        String username = claims.getSubject();
        Date expiration = claims.getExpiration();
        return username.equals(principal.getUsername()) && expiration.after(new Date());
    }

    private String generateToken(AppUserPrincipal principal, JwtTokenType type, long expirationMs, SecretKey key) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(principal.getUsername())
                .claim("role", principal.getRole().name())
                .claim("tokenType", type.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractClaims(String token, JwtTokenType type) {
        SecretKey key = type == JwtTokenType.ACCESS ? accessKey : refreshKey;
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
