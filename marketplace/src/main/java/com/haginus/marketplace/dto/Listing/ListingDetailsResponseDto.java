package com.haginus.marketplace.dto.Listing;

import com.haginus.marketplace.dto.Asset.AssetRequestDto;
import com.haginus.marketplace.dto.Asset.AssetResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListingDetailsResponseDto extends ListingResponseDto {
  private List<AssetResponseDto> assets;
}
