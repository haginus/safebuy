package com.haginus.common.clients.marketplace.dto.Listing;

import com.haginus.common.clients.marketplace.dto.Asset.AssetResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListingDetailsResponseDto extends ListingResponseDto {
  private List<AssetResponseDto> assets;
}
