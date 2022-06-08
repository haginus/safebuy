package com.haginus.common.clients.marketplace.dto.ListingCategory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingCategoryResponseDto {
  private Long id;
  private String name;
  private String icon;
}
