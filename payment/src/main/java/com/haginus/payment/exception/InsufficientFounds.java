package com.haginus.payment.exception;

import com.haginus.common.exception.CommonExceptionHandling;

@CommonExceptionHandling
public class InsufficientFounds extends RuntimeException {
  public InsufficientFounds() {
    super("Insufficient founds.");
  }
}
