package com.haginus.common.exception;

import org.springframework.http.HttpStatus;

@CommonExceptionHandling(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
  public ForbiddenException(String message) {
    super(message);
  }
  public ForbiddenException() {
    super("You are not allowed to perform this action.");
  }
}
