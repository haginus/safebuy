package com.haginus.marketplace.repository;

import com.haginus.marketplace.model.ListingOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListingOfferRepository extends JpaRepository<ListingOffer, Long> {
  Optional<ListingOffer> findByListing_Id(Long listingId);
}
