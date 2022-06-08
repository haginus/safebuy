package com.haginus.common.clients.payment.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
  private Long userId;
  private Double balance;
  private List<PaymentMethodResponseDto> paymentMethods;
}
