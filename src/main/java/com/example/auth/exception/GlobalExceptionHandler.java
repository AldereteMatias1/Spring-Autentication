package com.example.auth.exception;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
    var map = new HashMap<String, String>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> map.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.badRequest()
        .body(new ApiError(400, "Validation failed", map));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex) {
    return ResponseEntity.badRequest()
        .body(new ApiError(400, ex.getMessage(), null));
  }
}
