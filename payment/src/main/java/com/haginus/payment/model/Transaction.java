package com.haginus.payment.model;

import com.haginus.common.clients.payment.dto.Transaction.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
  @Id
  @Column(name = "id", nullable = false, length = 36)
  private String id;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "timestamp", nullable = false)
  private Timestamp timestamp;

  @OneToOne
  @JoinColumn(name = "payment_method_id")
  private PaymentMethod paymentMethod;

  @Enumerated(EnumType.STRING)
  private TransactionType type;

  @Column(name = "details", length = 1024)
  private String details;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

}
