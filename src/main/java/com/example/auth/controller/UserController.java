package com.example.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  @GetMapping("/public")
  public ResponseEntity<String> publicEndpoint() {
    return ResponseEntity.ok("public ok");
  }

  @GetMapping("/me")
  public ResponseEntity<String> me(Authentication auth) {
    return ResponseEntity.ok("hello " + auth.getName());
  }
}
