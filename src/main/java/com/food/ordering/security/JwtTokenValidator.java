package com.food.ordering.security;

import com.food.ordering.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Value("${security.token.secret}")
  private String secretKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader("Authorization");

    if (header != null) {
      var token = this.jwtProvider.validateToken(header);

      try {
        String email = String.valueOf(token.get("email"));
        String authorities = String.valueOf(token.get("authorities"));

        // ROLE_CUSTOMER, ROLE_ADMIN

        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        throw new BadCredentialsException("invalid token");
      }
    }

    filterChain.doFilter(request, response);
  }
}
