package com.haginus.marketplace.repository;

import com.haginus.marketplace.model.ListingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingCategoryRepository extends JpaRepository<ListingCategory, Long> {
}
