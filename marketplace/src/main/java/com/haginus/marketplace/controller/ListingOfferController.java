package com.haginus.marketplace.controller;

import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferRequestDto;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferResponseDto;
import com.haginus.marketplace.mapper.ListingOfferMapper;
import com.haginus.marketplace.model.ListingOffer;
import com.haginus.marketplace.service.ListingOfferService;
import com.haginus.marketplace.service.ListingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/listings/{listingId}/offer")
@AllArgsConstructor
public class ListingOfferController {

  private final ListingOfferService listingOfferService;
  private final ListingService listingService;
  private final ListingOfferMapper listingOfferMapper;

  @PostMapping
  public ResponseEntity<ListingOfferResponseDto> create(@Valid @RequestBody ListingOfferRequestDto dto,
                                                        @PathVariable Long listingId) {
    ListingOffer listingOffer = this.listingOfferMapper.toEntity(dto);
    listingOffer.setListing(this.listingService.get(listingId));
    ListingOffer result = this.listingOfferService.create(listingOffer);
    return ResponseEntity
      .created(URI.create(String.format("/listings/%s/offer", listingId)))
      .body(
        this.listingOfferMapper.toDto(result)
      );
  }

  @PostMapping("/seller-confirm")
  public ResponseEntity<ListingOfferResponseDto> sellerConfirm(@PathVariable Long listingId) {
    ListingOffer result = this.listingOfferService.sellerConfirm(listingId, null);
    return ResponseEntity.ok().body(this.listingOfferMapper.toDto(result));
  }

  @PostMapping("/buyer-confirm")
  public ResponseEntity<ListingOfferResponseDto> buyerConfirm(@PathVariable Long listingId) {
    ListingOffer result = this.listingOfferService.buyerConfirm(listingId, null);
    return ResponseEntity.ok().body(this.listingOfferMapper.toDto(result));
  }

  @PostMapping("/buyer-decline")
  public ResponseEntity<ListingOfferResponseDto> buyerDecline(@PathVariable Long listingId) {
    ListingOffer result = this.listingOfferService.buyerDecline(listingId, null);
    return ResponseEntity.ok().body(this.listingOfferMapper.toDto(result));
  }
}
