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
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "card_number", nullable = false, length = 16)
  private String cardNumber;

  @Column(name = "expiration", nullable = false)
  private String expiration;

  @Column(name = "cvv", nullable = false)
  private Integer cvv;

}
