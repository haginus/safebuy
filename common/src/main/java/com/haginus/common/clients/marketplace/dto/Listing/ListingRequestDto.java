package com.haginus.common.clients.marketplace.dto.Listing;

import com.haginus.common.clients.marketplace.dto.Asset.AssetRequestDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
