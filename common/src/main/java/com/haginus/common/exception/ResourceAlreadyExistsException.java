package com.haginus.common.exception;

@CommonExceptionHandling
public class ResourceAlreadyExistsException extends RuntimeException {
  public ResourceAlreadyExistsException(String message) {
    super(message);
  }
}
