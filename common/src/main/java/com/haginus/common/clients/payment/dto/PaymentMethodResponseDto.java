package com.haginus.common.clients.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMethodResponseDto {
  private Long id;
  private String ownerName;
  private String cardNumber;
  private String expiration;
}
