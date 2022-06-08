package com.haginus.user.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class KYCProcess {
  @Id
  @Column(name = "user_id")
  private Long userId;

  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "user_id")
  private User user;
}
