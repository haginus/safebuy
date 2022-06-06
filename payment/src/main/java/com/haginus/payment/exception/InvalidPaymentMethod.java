package com.haginus.payment.exception;

public class InvalidPaymentMethod extends RuntimeException {
  public InvalidPaymentMethod() {
    super("Invalid payment method.");
  }
}
