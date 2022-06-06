package com.haginus.payment.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDto {
  private Long userId;
  private Double balance;
  private List<PaymentMethodResponseDto> paymentMethods;
}
