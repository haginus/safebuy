package com.haginus.marketplace.dto.Listing;

import com.haginus.marketplace.dto.Asset.AssetRequestDto;
import com.haginus.marketplace.dto.ListingCategory.ListingCategoryResponseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class ListingResponseDto {
  private Long id;
  private String title;
  private String description;
  private boolean needsPersonalization;
  private ListingCategoryResponseDto listingCategory;
  private Long ownerId;
  private Double price;
}
