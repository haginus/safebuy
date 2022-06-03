package com.haginus.marketplace.repository;

import com.haginus.marketplace.model.ListingOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingOfferRepository extends JpaRepository<ListingOffer, Long> {
}
