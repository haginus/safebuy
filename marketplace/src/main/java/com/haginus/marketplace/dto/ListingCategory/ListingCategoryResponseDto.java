package com.haginus.marketplace.dto.ListingCategory;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ListingCategoryResponseDto {
  private Long id;
  private String name;
  private String icon;
}
