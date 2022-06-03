package com.haginus.marketplace.mapper;

import com.haginus.marketplace.dto.ListingCategory.ListingCategoryRequestDto;
import com.haginus.marketplace.dto.ListingCategory.ListingCategoryResponseDto;
import com.haginus.marketplace.model.ListingCategory;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ListingCategoryMapper {
  private final ModelMapper modelMapper;

  public ListingCategoryResponseDto toDto(ListingCategory entity) {
    return this.modelMapper.map(entity, ListingCategoryResponseDto.class);
  }

  public ListingCategory toEntity(ListingCategoryRequestDto dto) {
    return this.modelMapper.map(dto, ListingCategory.class);
  }
}
