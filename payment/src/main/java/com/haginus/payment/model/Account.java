package com.haginus.payment.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  @Id
  @Column(name = "id", nullable = false)
  private Long userId;

  @Column(name = "balance", nullable = false)
  private Double balance;

  @OneToMany(mappedBy = "account", orphanRemoval = true)
  private List<Transaction> transactions = new ArrayList<>();

  @OneToMany(mappedBy = "account", orphanRemoval = true)
  private List<PaymentMethod> paymentMethods = new ArrayList<>();

}
