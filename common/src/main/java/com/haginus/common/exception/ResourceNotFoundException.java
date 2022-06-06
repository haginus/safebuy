package com.haginus.common.exception;

import org.springframework.http.HttpStatus;

@CommonExceptionHandling(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
