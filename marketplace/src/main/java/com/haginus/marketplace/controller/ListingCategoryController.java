package com.haginus.marketplace.controller;

import com.haginus.common.clients.marketplace.dto.ListingCategory.ListingCategoryRequestDto;
import com.haginus.common.clients.marketplace.dto.ListingCategory.ListingCategoryResponseDto;
import com.haginus.marketplace.mapper.ListingCategoryMapper;
import com.haginus.marketplace.model.ListingCategory;
import com.haginus.marketplace.service.ListingCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class ListingCategoryController {

  private final ListingCategoryService listingCategoryService;
  private final ListingCategoryMapper listingCategoryMapper;

  @GetMapping
  public ResponseEntity<List<ListingCategoryResponseDto>> getAll() {
    return ResponseEntity.ok().body(
      this.listingCategoryService.getAll().stream()
        .map(this.listingCategoryMapper::toDto)
        .collect(Collectors.toList())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ListingCategoryResponseDto> get(@PathVariable Long id) {
    return ResponseEntity.ok().body(
      this.listingCategoryMapper.toDto(this.listingCategoryService.get(id))
    );
  }

  @PostMapping
  public ResponseEntity<ListingCategoryResponseDto> create(@Valid @RequestBody ListingCategoryRequestDto dto) {
    ListingCategory listingCategory = this.listingCategoryMapper.toEntity(dto);
    ListingCategory result = this.listingCategoryService.create(listingCategory);
    return ResponseEntity
      .created(URI.create("/categories/" + result.getId()))
      .body(this.listingCategoryMapper.toDto(result));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ListingCategoryResponseDto> update(@PathVariable Long id, @Valid @RequestBody ListingCategoryRequestDto dto) {
    ListingCategory listingCategory = this.listingCategoryMapper.toEntity(dto);
    listingCategory.setId(id);
    ListingCategory result = this.listingCategoryService.update(listingCategory);
    return ResponseEntity.ok().body(this.listingCategoryMapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    this.listingCategoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
