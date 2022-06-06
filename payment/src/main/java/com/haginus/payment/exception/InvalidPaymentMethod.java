package com.haginus.payment.exception;

import com.haginus.common.exception.CommonExceptionHandling;

@CommonExceptionHandling
public class InvalidPaymentMethod extends RuntimeException {
  public InvalidPaymentMethod() {
    super("Invalid payment method.");
  }
}
