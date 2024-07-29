package com.food.ordering.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JWTProvider {

  @Value("${security.token.secret}")
  private String secretKey;

  public Claims validateToken(String token) {
    token = token.replace("Bearer ", "");

    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    try {
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    } catch (JwtException e) {
      return null;
    }
  }
}
