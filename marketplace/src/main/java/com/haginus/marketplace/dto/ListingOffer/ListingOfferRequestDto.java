package com.haginus.marketplace.dto.ListingOffer;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ListingOfferRequestDto {
  @NotNull
  @Positive
  private Long buyerId;

  @NotEmpty
  private String paymentId;
}
