package com.haginus.common.exception;

import org.springframework.http.HttpStatus;

@CommonExceptionHandling(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceCommunicationException extends RuntimeException {
  public ServiceCommunicationException(String message) {
    super(message);
  }
}
