package com.haginus.payment.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;


@Data
public class PaymentMethodResponseDto {
  private Long id;
  private String ownerName;
  private String cardNumber;
  private String expiration;
}
