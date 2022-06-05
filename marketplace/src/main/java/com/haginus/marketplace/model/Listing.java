package com.haginus.marketplace.model;

import com.haginus.marketplace.model.Asset.Asset;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Listing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", nullable = false, length = 2048)
  private String description;

  @Column(name = "needs_personalization")
  private boolean needsPersonalization;

  @ManyToOne(optional = false)
  @JoinColumn(name = "listing_category_id", nullable = false)
  private ListingCategory listingCategory;

  @Column(name = "owner_id", nullable = false)
  private Long ownerId;

  private Double price;

  @OneToOne(mappedBy = "listing", orphanRemoval = true)
  private ListingOffer listingOffer;

  @OneToMany(mappedBy = "listing", orphanRemoval = true)
  private List<Asset> assets = new ArrayList<>();

  public boolean isAvailable() {
    return listingOffer == null;
  }

  public boolean isFinished() {
    return listingOffer != null && !listingOffer.getStatus().isPending();
  }

  public boolean needsPersonalization() {
    return needsPersonalization;
  }
}
