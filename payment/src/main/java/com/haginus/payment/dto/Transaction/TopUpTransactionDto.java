package com.haginus.payment.dto.Transaction;

import com.haginus.payment.dto.PaymentMethodRequestDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TopUpTransactionDto {
  @NotNull
  private Double amount;

  @NotNull
  @Valid
  PaymentMethodRequestDto paymentMethod;
}
