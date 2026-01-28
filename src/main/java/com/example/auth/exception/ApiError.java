package com.example.auth.exception;

import java.time.Instant;
import java.util.Map;

public class ApiError {
  private final Instant timestamp = Instant.now();
  private final int status;
  private final String error;
  private final Map<String, String> details;

  public ApiError(int status, String error, Map<String, String> details) {
    this.status = status;
    this.error = error;
    this.details = details;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public Map<String, String> getDetails() {
    return details;
  }
}
