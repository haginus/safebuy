package com.haginus.payment.model;

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
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "timestamp", nullable = false)
  private Timestamp timestamp;

  @OneToOne
  @JoinColumn(name = "payment_method_id")
  private PaymentMethod paymentMethod;

  @Column(name = "for_listing_id")
  private Long forListingId;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

}
