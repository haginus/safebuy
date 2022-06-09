package com.haginus.marketplace.mapper;

import com.haginus.common.clients.marketplace.dto.Listing.ListingDetailsResponseDto;
import com.haginus.common.clients.marketplace.dto.Listing.ListingRequestDto;
import com.haginus.common.clients.marketplace.dto.Listing.ListingResponseDto;
import com.haginus.common.clients.user.UserClient;
import com.haginus.common.clients.user.dto.UserResponseDto;
import com.haginus.marketplace.model.Listing;
import com.haginus.marketplace.service.ListingCategoryService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ListingMapper {

  private final ListingCategoryService listingCategoryService;
  private final ListingCategoryMapper listingCategoryMapper;
  private final AssetMapper assetMapper;
  private final ListingOfferMapper listingOfferMapper;
  private final ModelMapper modelMapper;
  private final UserClient userClient;

  public ListingMapper(ListingCategoryService listingCategoryService, ListingCategoryMapper listingCategoryMapper,
                       AssetMapper assetMapper, UserClient userClient, ListingOfferMapper listingOfferMapper) {
    this.listingCategoryService = listingCategoryService;
    this.listingCategoryMapper = listingCategoryMapper;
    this.assetMapper = assetMapper;
    this.listingOfferMapper = listingOfferMapper;
    this.userClient = userClient;
    this.modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setAmbiguityIgnored(true);
    modelMapper.addMappings(new PropertyMap<ListingRequestDto, Listing>() {
      @Override
      protected void configure() {
        skip(destination.getAssets());
        skip(destination.getListingOffer());
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

  @CircuitBreaker(name="userDetails", fallbackMethod = "toDtoFallback")
  public ListingResponseDto toDto(Listing entity, boolean showDetails) {
    ListingResponseDto dto = this.toDtoMaker(entity, showDetails);
    UserResponseDto owner = this.userClient.getUser(entity.getOwnerId());
    dto.setOwner(owner);
    return dto;
  }

  private ListingResponseDto toDtoFallback(Listing entity, boolean showDetails, Throwable throwable) {
    ListingResponseDto dto = this.toDtoMaker(entity, showDetails);
    dto.setOwner(null);
    return dto;
  }

  private ListingResponseDto toDtoFallback(Listing entity, Throwable throwable) {
    return this.toDtoFallback(entity, false, throwable);
  }

  private ListingResponseDto toDtoMaker(Listing entity, boolean showDetails) {
    ListingResponseDto dto;
    if(showDetails) {
      dto = modelMapper.map(entity, ListingDetailsResponseDto.class);
      ((ListingDetailsResponseDto) dto).setAssets(
        entity.getAssets().stream().map(assetMapper::toDto).collect(Collectors.toList())
      );
      ((ListingDetailsResponseDto) dto).setListingOffer(
        this.listingOfferMapper.toDto(entity.getListingOffer())
      );
    } else {
      dto = modelMapper.map(entity, ListingResponseDto.class);
    }
    dto.setListingCategory(this.listingCategoryMapper.toDto(entity.getListingCategory()));
    return dto;
  }

  @CircuitBreaker(name="userDetails", fallbackMethod = "toDtoFallback")
  public ListingResponseDto toDto(Listing entity) {
    return this.toDto(entity, false);
  }

}
