package com.haginus.marketplace.dto.Listing;

import com.haginus.marketplace.dto.Asset.AssetRequestDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class ListingRequestDto {
  @NotEmpty
  private String title;

  @NotEmpty
  private String description;

  private boolean needsPersonalization;

  @NotNull
  @Positive
  private Long listingCategoryId;

  @NotNull
  @Positive
  private Long ownerId;

  @NotNull
  @Positive
  private Double price;

  @NotEmpty
  private List<@Valid AssetRequestDto> assets;
}
