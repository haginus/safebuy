package com.haginus.payment.exception;

public class InsufficientFounds extends RuntimeException {
  public InsufficientFounds() {
    super("Insufficient founds.");
  }
}
