package com.example.auth.config;

import com.example.auth.model.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final Key key;
  private final long expirationMinutes;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.expirationMinutes}") long expirationMinutes
  ) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationMinutes = expirationMinutes;
  }

  public String createToken(String email, Set<Role> roles) {
    Instant now = Instant.now();
    Instant exp = now.plus(expirationMinutes, ChronoUnit.MINUTES);

    return Jwts.builder()
        .subject(email)
        .claim("roles", roles.stream().map(Enum::name).toList())
        .issuedAt(Date.from(now))
        .expiration(Date.from(exp))
        .signWith(key)
        .compact();
  }

  public String extractEmail(String token) {
    return Jwts.parser().verifyWith((javax.crypto.SecretKey) key).build()
        .parseSignedClaims(token).getPayload().getSubject();
  }
}
