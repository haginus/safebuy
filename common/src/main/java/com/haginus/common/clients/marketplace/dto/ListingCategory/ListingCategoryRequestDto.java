package com.haginus.common.clients.marketplace.dto.ListingCategory;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingCategoryRequestDto {

  @NotEmpty
  @Length(min = 3)
  private String name;

  @NotEmpty
  private String icon;
}
