package com.haginus.common.clients.payment.dto.Transaction;

import com.haginus.common.clients.payment.dto.PaymentMethodResponseDto;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDto {
  private String id;
  private Double amount;
  private Timestamp timestamp;
  private PaymentMethodResponseDto paymentMethod;
  private TransactionType type;
  private String details;
}
