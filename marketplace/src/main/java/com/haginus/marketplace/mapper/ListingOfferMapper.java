package com.haginus.marketplace.mapper;

import com.haginus.marketplace.dto.ListingOffer.ListingOfferRequestDto;
import com.haginus.marketplace.dto.ListingOffer.ListingOfferResponseDto;
import com.haginus.marketplace.model.ListingOffer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class ListingOfferMapper {

  private final ModelMapper modelMapper;

  ListingOfferMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
    this.modelMapper.addMappings(new PropertyMap<ListingOfferRequestDto, ListingOffer>() {
      @Override
      protected void configure() {
        skip(destination.getId());
        skip(destination.getListing());
      }
    });
  }

  public ListingOfferResponseDto toDto(ListingOffer entity) {
    return this.modelMapper.map(entity, ListingOfferResponseDto.class);
  }

  public ListingOffer toEntity(ListingOfferRequestDto dto) {
    return this.modelMapper.map(dto, ListingOffer.class);
  }
}
