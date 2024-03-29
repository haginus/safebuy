package com.haginus.marketplace.service;

import com.haginus.common.clients.payment.PaymentClient;
import com.haginus.common.clients.payment.dto.Transaction.MarketplaceTransactionDto;
import com.haginus.common.clients.payment.dto.Transaction.TransactionResponseDto;
import com.haginus.common.exception.*;
import com.haginus.marketplace.model.Listing;
import com.haginus.marketplace.model.ListingOffer;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferStatus;
import com.haginus.marketplace.repository.ListingOfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListingOfferService {

  private final ListingOfferRepository listingOfferRepository;
  private final ListingService listingService;
  private final PaymentClient paymentClient;

  public ListingOffer get(Long id) {
    Optional<ListingOffer> listingOffer = this.listingOfferRepository.findById(id);
    return listingOffer.orElseThrow(() -> new ResourceNotFoundException("Listing offer does not exist."));
  }

  public ListingOffer getByListingId(Long id) {
    Optional<ListingOffer> listingOffer = this.listingOfferRepository.findByListing_Id(id);
    return listingOffer.orElseThrow(() -> new ResourceNotFoundException("Listing offer does not exist."));
  }

  public ListingOffer create(ListingOffer listingOffer) {
    if(listingOffer.getId() != null) {
      Optional<ListingOffer> optional = this.listingOfferRepository.findById(listingOffer.getId());
      if(optional.isPresent()) {
        throw new ResourceAlreadyExistsException("Listing offer already exists.");
      }
    }
    Listing listing = listingOffer.getListing();
    if(!listing.isAvailable()) {
      throw new NotAllowedException("This listing is not available.");
    }
    if(listing.needsPersonalization()) {
      listingOffer.setStatus(ListingOfferStatus.PENDING_SELLER_ACTION);
    } else {
      listingOffer.setStatus(ListingOfferStatus.PENDING_BUYER_CONFIRMATION);
    }
    this.setTimestamps(listingOffer, 1000 * 60 * 60 * 24);
    return this.listingOfferRepository.save(listingOffer);
  }

  public ListingOffer sellerConfirm(Long listingId, Long sellerId) {
    ListingOffer listingOffer = this.getByListingId(listingId);
    if(sellerId != null && !Objects.equals(listingOffer.getListing().getOwnerId(), sellerId)) {
      throw new ForbiddenException();
    }
    if(listingOffer.getStatus() != ListingOfferStatus.PENDING_SELLER_ACTION) {
      throw new NotAllowedException();
    }
    listingOffer.setStatus(ListingOfferStatus.PENDING_BUYER_CONFIRMATION);
    this.setTimestamps(listingOffer, 1000 * 60 * 60 * 12);
    return this.listingOfferRepository.save(listingOffer);
  }

  public ListingOffer buyerConfirm(Long listingId, Long buyerId) {
    ListingOffer listingOffer = this.getByListingId(listingId);
    if(buyerId != null && !Objects.equals(listingOffer.getBuyerId(), buyerId)) {
      throw new ForbiddenException();
    }
    if(listingOffer.getStatus() != ListingOfferStatus.PENDING_BUYER_CONFIRMATION) {
      throw new NotAllowedException();
    }
    listingOffer.setStatus(ListingOfferStatus.ACCEPTED_BY_BUYER);
    this.setTimestamps(listingOffer, 0);

    Listing listing = listingOffer.getListing();

    try {
      TransactionResponseDto paymentResult = this.paymentClient.marketplaceListingSold(
        MarketplaceTransactionDto.builder()
          .accountId(listing.getOwnerId())
          .listingId(listingId)
          .amount(listing.getPrice())
          .build()
      );
    } catch (Exception e) {
      System.out.println(e);
      throw new ServiceCommunicationException("There was a problem in communicating with the Payment service.");
    }

    return this.listingOfferRepository.save(listingOffer);
  }

  public ListingOffer buyerDecline(Long listingId, Long buyerId) {
    ListingOffer listingOffer = this.getByListingId(listingId);
    if(buyerId != null && !Objects.equals(listingOffer.getBuyerId(), buyerId)) {
      throw new ForbiddenException();
    }
    if(listingOffer.getStatus() != ListingOfferStatus.PENDING_BUYER_CONFIRMATION) {
      throw new NotAllowedException();
    }
    listingOffer.setStatus(ListingOfferStatus.PENDING_DISPUTE);
    this.setTimestamps(listingOffer, 1000 * 60 * 60 * 24 * 7);
    return this.listingOfferRepository.save(listingOffer);
  }

  private void setTimestamps(ListingOffer listingOffer, long expiryTime) {
    listingOffer.setLastActionTimestamp(new Timestamp(System.currentTimeMillis()));
    if(expiryTime == 0) {
      listingOffer.setExpiryTimestamp(null);
    } else {
      listingOffer.setExpiryTimestamp(new Timestamp(System.currentTimeMillis() + expiryTime));
    }
  }


}
