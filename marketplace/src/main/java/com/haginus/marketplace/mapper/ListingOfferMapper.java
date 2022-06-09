package com.haginus.marketplace.mapper;

import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferRequestDto;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferResponseDto;
import com.haginus.common.clients.user.UserClient;
import com.haginus.marketplace.model.ListingOffer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class ListingOfferMapper {

  private final ModelMapper modelMapper;
  private final UserClient userClient;

  ListingOfferMapper(ModelMapper modelMapper, UserClient userClient) {
    this.userClient = userClient;
    this.modelMapper = modelMapper;
    this.modelMapper.addMappings(new PropertyMap<ListingOfferRequestDto, ListingOffer>() {
      @Override
      protected void configure() {
        skip(destination.getId());
        skip(destination.getListing());
      }
    });
  }

  @CircuitBreaker(name="userDetails", fallbackMethod = "toDtoFallback")
  public ListingOfferResponseDto toDto(ListingOffer entity) {
    ListingOfferResponseDto dto = this.modelMapper.map(entity, ListingOfferResponseDto.class);
    dto.setBuyer(this.userClient.getUser(entity.getBuyerId()));
    return dto;
  }

  private ListingOfferResponseDto toDtoFallback(ListingOffer entity, Throwable throwable) {
    ListingOfferResponseDto dto = this.modelMapper.map(entity, ListingOfferResponseDto.class);
    dto.setBuyer(null);
    return dto;
  }

  public ListingOffer toEntity(ListingOfferRequestDto dto) {
    return this.modelMapper.map(dto, ListingOffer.class);
  }
}
