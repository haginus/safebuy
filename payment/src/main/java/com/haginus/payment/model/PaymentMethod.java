package com.haginus.payment.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "owner_name", nullable = false)
  private String ownerName;

  @Column(name = "card_number", nullable = false, length = 16)
  private String cardNumber;

  @Column(name = "expiration", nullable = false)
  private String expiration;

  @Column(name = "cvv", nullable = false)
  private String cvv;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

}
