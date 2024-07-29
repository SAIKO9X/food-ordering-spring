package com.food.ordering.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JWTProvider {

  @Value("${security.token.secret}")
  private String secretKey;

  @Value("${security.token.expiration}")
  private long expirationTime;

  public Claims validateToken(String token) {
    token = token.replace("Bearer ", "");

    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    try {
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    } catch (JwtException e) {
      return null;
    }
  }

  public String generateToken(Authentication auth) {
    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    String roles = populateAuthorities(authorities);

    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    return Jwts.builder()
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + expirationTime))
      .claim("email", auth.getName())
      .claim("authorities", roles)
      .signWith(key)
      .compact();
  }


  public String getEmailFromJwtToken(String header) {
    var token = validateToken(header);
    return String.valueOf(token.get("email"));
  }

  private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
    Set<String> auths = authorities.stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toSet());

    return String.join(",", auths);
  }
}
