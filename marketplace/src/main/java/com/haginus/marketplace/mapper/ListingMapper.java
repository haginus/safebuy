package com.haginus.marketplace.mapper;

import com.haginus.marketplace.dto.Listing.ListingDetailsResponseDto;
import com.haginus.marketplace.dto.Listing.ListingRequestDto;
import com.haginus.marketplace.dto.Listing.ListingResponseDto;
import com.haginus.marketplace.model.Listing;
import com.haginus.marketplace.service.ListingCategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ListingMapper {

  private final ListingCategoryService listingCategoryService;
  private final ListingCategoryMapper listingCategoryMapper;
  private final AssetMapper assetMapper;
  private final ModelMapper modelMapper;

  public ListingMapper(ListingCategoryService listingCategoryService, ListingCategoryMapper listingCategoryMapper, AssetMapper assetMapper) {
    this.listingCategoryService = listingCategoryService;
    this.listingCategoryMapper = listingCategoryMapper;
    this.assetMapper = assetMapper;
    this.modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setAmbiguityIgnored(true);
    modelMapper.addMappings(new PropertyMap<ListingRequestDto, Listing>() {
      @Override
      protected void configure() {
        skip(destination.getAssets());
      }
    });
  }

  public Listing toEntity(ListingRequestDto dto) {
    Listing listing = modelMapper.map(dto, Listing.class);
    listing.setId(null);
    listing.setListingCategory(this.listingCategoryService.get(dto.getListingCategoryId()));
    listing.setAssets(
      dto.getAssets().stream().map(assetMapper::toEntity).collect(Collectors.toList())
    );
    return listing;
  }

  public ListingResponseDto toDto(Listing entity, boolean showDetails) {
    ListingResponseDto dto;
    if(showDetails) {
      dto = modelMapper.map(entity, ListingDetailsResponseDto.class);
      ((ListingDetailsResponseDto) dto).setAssets(
        entity.getAssets().stream().map(assetMapper::toDto).collect(Collectors.toList())
      );
    } else {
      dto = modelMapper.map(entity, ListingResponseDto.class);
    }
    dto.setListingCategory(this.listingCategoryMapper.toDto(entity.getListingCategory()));
    return dto;
  }

  public ListingResponseDto toDto(Listing entity) {
    return this.toDto(entity, false);
  }

}
