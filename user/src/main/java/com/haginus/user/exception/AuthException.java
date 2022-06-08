package com.haginus.user.exception;

import com.haginus.common.exception.CommonExceptionHandling;

@CommonExceptionHandling
public class AuthException extends RuntimeException {
  public AuthException(String message) {
    super(message);
  }
}
