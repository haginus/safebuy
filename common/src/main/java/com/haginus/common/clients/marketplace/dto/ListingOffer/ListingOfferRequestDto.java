package com.haginus.common.clients.marketplace.dto.ListingOffer;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingOfferRequestDto {
  @NotNull
  @Positive
  private Long buyerId;

  @NotEmpty
  private String paymentId;
}
