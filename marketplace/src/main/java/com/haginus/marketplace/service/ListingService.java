package com.haginus.marketplace.service;

import com.haginus.common.exception.NotAllowedException;
import com.haginus.common.exception.ResourceAlreadyExistsException;
import com.haginus.common.exception.ResourceNotFoundException;
import com.haginus.marketplace.model.Asset.Asset;
import com.haginus.marketplace.model.Listing;
import com.haginus.marketplace.model.ListingOffer;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferStatus;
import com.haginus.marketplace.repository.ListingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ListingService {
  private final ListingRepository listingRepository;
  private final AssetService assetService;

  public List<Listing> getAll() {
    return this.listingRepository.findAll();
  }

  public Listing get(Long id) {
    Optional<Listing> optional = this.listingRepository.findById(id);
    return optional.orElseThrow(() -> new ResourceNotFoundException("Listing does not exist."));
  }

  public Listing create(Listing listing) {
    if(listing.getId() != null) {
      Optional<Listing> optional = this.listingRepository.findById(listing.getId());
      if(optional.isPresent()) {
        throw new ResourceAlreadyExistsException("Listing already exists.");
      }
    }
    Listing result = this.listingRepository.save(listing);
    listing.getAssets().forEach(asset -> {
      asset.setListing(listing);
      this.assetService.create(asset);
    });
    return result;
  }

  public Listing update(Listing listing) {
    Listing existingListing = this.get(listing.getId());
    if(!existingListing.isAvailable()) {
      throw new NotAllowedException("Listing cannot be updated.");
    }
    if(!Objects.equals(existingListing.getOwnerId(), listing.getOwnerId())) {
      throw new NotAllowedException("Listing ownership cannot be transferred.");
    }

    // Delete
    existingListing.getAssets().stream()
      .filter(asset -> listing.getAssets().stream().noneMatch(existingAsset -> Objects.equals(existingAsset.getId(), asset.getId())))
      .forEach(this.assetService::delete);

    // Create
    listing.getAssets().stream()
      .filter(asset -> asset.getId() == null)
      .forEach(asset -> {
        asset.setListing(listing);
        this.assetService.create(asset);
      });

    this.listingRepository.save(listing);
    return listing;
  }

  public Listing addAssets(Long listingId, List<Asset> assets) {
    if(assets.stream().anyMatch(asset -> asset.getId() != null)) {
      throw new ResourceAlreadyExistsException("Asset cannot have an ID here.");
    }
    Listing listing = this.get(listingId);
    ListingOffer listingOffer = listing.getListingOffer();
    if(listingOffer == null) {
      throw new NotAllowedException("Use update() instead.");
    }
    if(listingOffer.getStatus() != ListingOfferStatus.PENDING_BUYER_CONFIRMATION) {
      throw new NotAllowedException("Assets cannot be added anymore.");
    }
    assets.forEach(asset -> {
      asset.setListing(listing);
      this.assetService.create(asset);
    });
    return listing;
  }

  public void delete(Long id) {
    Listing listing = this.get(id);
    if(listing.isFinished() || !listing.isAvailable()) {
      throw new NotAllowedException("Listing cannot be deleted.");
    }
    this.listingRepository.delete(listing);
  }
}
