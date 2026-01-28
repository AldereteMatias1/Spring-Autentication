package com.example.auth.service;

import com.example.auth.config.JwtService;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.model.AppUser;
import com.example.auth.model.Role;
import com.example.auth.repository.UserRepository;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public String register(RegisterRequest req) {
    if (userRepository.existsByEmail(req.getEmail())) {
      throw new IllegalArgumentException("Email already registered");
    }

    var user = new AppUser(
        req.getEmail(),
        passwordEncoder.encode(req.getPassword()),
        Set.of(Role.ROLE_USER)
    );
    userRepository.save(user);
    return jwtService.createToken(user.getEmail(), user.getRoles());
  }

  public String login(LoginRequest req) {
    AppUser user = userRepository.findByEmail(req.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

    if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    return jwtService.createToken(user.getEmail(), user.getRoles());
  }
}
