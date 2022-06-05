package com.haginus.marketplace.controller;

import com.haginus.marketplace.dto.Asset.AssetRequestDto;
import com.haginus.marketplace.dto.Listing.ListingRequestDto;
import com.haginus.marketplace.dto.Listing.ListingResponseDto;
import com.haginus.marketplace.mapper.AssetMapper;
import com.haginus.marketplace.mapper.ListingMapper;
import com.haginus.marketplace.model.Asset.Asset;
import com.haginus.marketplace.model.Listing;
import com.haginus.marketplace.service.ListingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/listings")
@AllArgsConstructor
public class ListingController {

  private final ListingService listingService;
  private final ListingMapper listingMapper;
  private final AssetMapper assetMapper;

  @GetMapping
  public ResponseEntity<List<ListingResponseDto>> getAll() {
    return ResponseEntity.ok().body(
      this.listingService.getAll().stream()
        .map(this.listingMapper::toDto)
        .collect(Collectors.toList())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ListingResponseDto> get(@PathVariable Long id) {
    return ResponseEntity.ok().body(
      this.listingMapper.toDto(this.listingService.get(id))
    );
  }

  @GetMapping("/{id}/details")
  public ResponseEntity<ListingResponseDto> getDetailed(@PathVariable Long id) {
    return ResponseEntity.ok().body(
      this.listingMapper.toDto(this.listingService.get(id), true)
    );
  }

  @PostMapping
  public ResponseEntity<ListingResponseDto> create(@Valid @RequestBody ListingRequestDto dto) {
    Listing listing = this.listingMapper.toEntity(dto);
    Listing result = this.listingService.create(listing);
    return ResponseEntity
      .created(URI.create("/listings/" + result.getId()))
      .body(this.listingMapper.toDto(result, true));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ListingResponseDto> update(@Valid @RequestBody ListingRequestDto dto, @PathVariable Long id) {
    Listing listing = this.listingMapper.toEntity(dto);
    listing.setId(id);
    Listing result = this.listingService.update(listing);
    return ResponseEntity.ok().body(this.listingMapper.toDto(result, true));
  }

  @PostMapping("/{id}/assets")
  public ResponseEntity<ListingResponseDto> update(@RequestBody List<@Valid AssetRequestDto> dtoList, @PathVariable Long id) {
    List<Asset> assets = dtoList.stream().map(this.assetMapper::toEntity).collect(Collectors.toList());
    Listing result = this.listingService.addAssets(id, assets);
    return ResponseEntity.ok().body(this.listingMapper.toDto(result, true));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    this.listingService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
