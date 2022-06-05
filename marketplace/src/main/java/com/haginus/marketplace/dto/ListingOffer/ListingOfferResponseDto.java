package com.haginus.marketplace.dto.ListingOffer;

import com.haginus.marketplace.model.ListingOfferStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ListingOfferResponseDto {
  private Long buyerId;
  private String paymentId;
  private ListingOfferStatus status;
  private Timestamp lastActionTimestamp;
  private Timestamp expiryTimestamp;
}
