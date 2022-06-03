package com.haginus.marketplace.dto.ListingCategory;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ListingCategoryRequestDto {

  @NotEmpty
  @Length(min = 3)
  private String name;

  @NotEmpty
  private String icon;
}
