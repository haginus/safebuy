package com.haginus.marketplace.service;

import com.haginus.marketplace.exception.ForbiddenException;
import com.haginus.marketplace.exception.ResourceAlreadyExistsException;
import com.haginus.marketplace.exception.ResourceNotFoundException;
import com.haginus.marketplace.model.Asset.Asset;
import com.haginus.marketplace.model.Listing;
import com.haginus.marketplace.model.ListingCategory;
import com.haginus.marketplace.repository.AssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssetService {
  private final AssetRepository assetRepository;

  public Asset create(Asset asset) {
    if(asset.getId() != null) {
      Optional<Asset> optional = this.assetRepository.findById(asset.getId());
      if(optional.isPresent()) {
        throw new ResourceAlreadyExistsException("Asset already exists.");
      }
    }
    return this.assetRepository.save(asset);
  }

  public Asset get(Long id) {
    Optional<Asset> optional = this.assetRepository.findById(id);
    return optional.orElseThrow(() -> new ResourceNotFoundException("Asset does not exist."));
  }

  public Asset getSecure(Long id, Long userId) {
    Asset asset = this.get(id);
    Listing listing = asset.getListing();
    if(!Objects.equals(userId, listing.getOwnerId()) && !Objects.equals(userId, listing.getListingOffer().getBuyerId())) {
      throw new ForbiddenException("You cannot access this resource.");
    }
    return asset;
  }


}
