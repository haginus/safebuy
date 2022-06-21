package com.haginus.common.clients.marketplace.dto.ListingCategory;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingCategoryResponseDto extends RepresentationModel<ListingCategoryResponseDto> {
  private Long id;
  private String name;
  private String icon;
}
