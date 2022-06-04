package com.haginus.marketplace.repository;

import com.haginus.marketplace.model.Asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
