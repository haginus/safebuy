package com.haginus.common.clients.marketplace.dto.Listing;

import com.haginus.common.clients.marketplace.dto.ListingCategory.ListingCategoryResponseDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingResponseDto {
  private Long id;
  private String title;
  private String description;
  private boolean needsPersonalization;
  private ListingCategoryResponseDto listingCategory;
  private Long ownerId;
  private Double price;
}
