package com.haginus.common.clients.payment.dto.Transaction;

import com.haginus.common.clients.payment.dto.PaymentMethodRequestDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopUpTransactionDto {
  @NotNull
  private Double amount;

  @NotNull
  @Valid
  PaymentMethodRequestDto paymentMethod;
}
