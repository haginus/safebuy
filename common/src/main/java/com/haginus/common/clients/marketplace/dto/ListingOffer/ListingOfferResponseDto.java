package com.haginus.common.clients.marketplace.dto.ListingOffer;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingOfferResponseDto {
  private Long buyerId;
  private String paymentId;
  private ListingOfferStatus status;
  private Timestamp lastActionTimestamp;
  private Timestamp expiryTimestamp;
}
