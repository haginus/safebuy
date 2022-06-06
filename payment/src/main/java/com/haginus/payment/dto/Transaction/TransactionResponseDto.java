package com.haginus.payment.dto.Transaction;

import com.haginus.payment.dto.PaymentMethodResponseDto;
import com.haginus.payment.model.TransactionType;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionResponseDto {
  private String id;
  private Double amount;
  private Timestamp timestamp;
  private PaymentMethodResponseDto paymentMethod;
  private TransactionType type;
  private String details;
}
