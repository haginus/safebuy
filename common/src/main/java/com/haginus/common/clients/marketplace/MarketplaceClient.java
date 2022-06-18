package com.haginus.common.clients.marketplace;

import com.haginus.common.clients.marketplace.dto.Listing.ListingResponseDto;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferRequestDto;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "marketplace", url = "${clients.marketplace.url:#{null}}")
public interface MarketplaceClient {
  @PostMapping("/listings/{listingId}/offer")
  ListingOfferResponseDto createOffer(@RequestBody ListingOfferRequestDto dto,
                                      @PathVariable("listingId") Long listingId);

  @GetMapping("/listings/{id}/details")
  ListingResponseDto getDetailedOffer(@PathVariable("id") Long id);

  @GetMapping("/listings/{id}")
  ListingResponseDto getOffer(@PathVariable("id") Long id);


}
