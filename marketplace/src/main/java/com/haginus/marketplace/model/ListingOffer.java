package com.haginus.marketplace.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ListingOffer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private Long buyerId;

  private String paymentId;

  @Enumerated(EnumType.STRING)
  private ListingOfferStatus status;

  @OneToOne(optional = false, orphanRemoval = true)
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;

}
