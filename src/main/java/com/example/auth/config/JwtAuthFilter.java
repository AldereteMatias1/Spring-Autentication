package com.example.auth.config;

import com.example.auth.model.AppUser;
import com.example.auth.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (auth == null || !auth.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    String token = auth.substring(7);
    try {
      String email = jwtService.extractEmail(token);

      AppUser user = userRepository.findByEmail(email).orElse(null);
      if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        var authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .toList();

        var authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception ignored) {
      // Invalid token, continue without authentication.
    }

    chain.doFilter(request, response);
  }
}
