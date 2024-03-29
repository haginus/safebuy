package com.haginus.common.exception;

@CommonExceptionHandling
public class NotAllowedException extends RuntimeException {
  public NotAllowedException(String message) {
    super(message);
  }
  public NotAllowedException() {
    super("You cannot perform this action.");
  }
}
