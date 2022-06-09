package com.haginus.common.clients.marketplace.dto.Listing;

import com.haginus.common.clients.marketplace.dto.Asset.AssetResponseDto;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListingDetailsResponseDto extends ListingResponseDto {
  private List<AssetResponseDto> assets;
  private ListingOfferResponseDto listingOffer;
}
