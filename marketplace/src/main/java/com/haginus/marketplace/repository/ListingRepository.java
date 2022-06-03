package com.haginus.marketplace.repository;

import com.haginus.marketplace.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
