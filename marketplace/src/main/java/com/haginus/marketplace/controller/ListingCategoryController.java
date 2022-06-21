package com.haginus.marketplace.controller;

import com.haginus.common.clients.marketplace.dto.ListingCategory.ListingCategoryRequestDto;
import com.haginus.common.clients.marketplace.dto.ListingCategory.ListingCategoryResponseDto;
import com.haginus.marketplace.mapper.ListingCategoryMapper;
import com.haginus.marketplace.model.ListingCategory;
import com.haginus.marketplace.service.ListingCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

  @GetMapping(produces = {"application/json"})
  public ResponseEntity<List<ListingCategoryResponseDto>> getAllJson() {
    List<ListingCategoryResponseDto> dtoList = this.listingCategoryService.getAll().stream()
      .map(this.listingCategoryMapper::toDto)
      .collect(Collectors.toList());
    return ResponseEntity.ok().body(dtoList);
  }

  @GetMapping(produces = {"application/hal+json"})
  public ResponseEntity<CollectionModel<ListingCategoryResponseDto>> getAll() {
    List<ListingCategoryResponseDto> dtoList = this.listingCategoryService.getAll().stream()
      .map(this.listingCategoryMapper::toDto)
      .map(this::addLinks)
      .collect(Collectors.toList());

    Link link = linkTo(methodOn(ListingCategoryController.class).getAll()).withSelfRel();
    CollectionModel<ListingCategoryResponseDto> collectionModel = CollectionModel.of(dtoList, link);
    return ResponseEntity.ok().body(collectionModel);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<ListingCategoryResponseDto> getJson(@PathVariable Long id) {
    ListingCategoryResponseDto dto =  this.listingCategoryMapper.toDto(this.listingCategoryService.get(id));
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping(value = "/{id}", produces = "application/hal+json")
  public ResponseEntity<ListingCategoryResponseDto> get(@PathVariable Long id) {
    ListingCategoryResponseDto dto =  this.listingCategoryMapper.toDto(this.listingCategoryService.get(id));
    this.addLinks(dto);
    return ResponseEntity.ok().body(dto);
  }

  private ListingCategoryResponseDto _create(ListingCategoryRequestDto dto) {
    ListingCategory listingCategory = this.listingCategoryMapper.toEntity(dto);
    ListingCategory result = this.listingCategoryService.create(listingCategory);
    return this.listingCategoryMapper.toDto(result);
  }

  @PostMapping(produces = "application/json")
  public ResponseEntity<ListingCategoryResponseDto> createJson(@Valid @RequestBody ListingCategoryRequestDto dto) {
    ListingCategoryResponseDto resultDto = _create(dto);
    this.addLinks(resultDto);
    URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{categoryId}").buildAndExpand(resultDto.getId())
      .toUri();
    return ResponseEntity.created(locationUri).body(resultDto);
  }

  @PostMapping(produces = "application/hal+json")
  public ResponseEntity<ListingCategoryResponseDto> create(@Valid @RequestBody ListingCategoryRequestDto dto) {
    ListingCategoryResponseDto resultDto = _create(dto);
    this.addLinks(resultDto);
    URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{categoryId}").buildAndExpand(resultDto.getId())
      .toUri();
    return ResponseEntity.created(locationUri).body(resultDto);
  }

  private ListingCategoryResponseDto _update(Long id, ListingCategoryRequestDto dto) {
    ListingCategory listingCategory = this.listingCategoryMapper.toEntity(dto);
    listingCategory.setId(id);
    ListingCategory result = this.listingCategoryService.update(listingCategory);
    return this.listingCategoryMapper.toDto(result);
  }

  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<ListingCategoryResponseDto> updateJson(@PathVariable Long id,
                                                           @Valid @RequestBody ListingCategoryRequestDto dto) {
    ListingCategoryResponseDto resultDto = _update(id, dto);
    return ResponseEntity.ok().body(resultDto);
  }

  @PutMapping(value = "/{id}", produces = "application/hal+json")
  public ResponseEntity<ListingCategoryResponseDto> update(@PathVariable Long id,
                                                           @Valid @RequestBody ListingCategoryRequestDto dto) {
    ListingCategoryResponseDto resultDto = _update(id, dto);
    this.addLinks(resultDto);
    return ResponseEntity.ok().body(resultDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    this.listingCategoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

  private ListingCategoryResponseDto addLinks(ListingCategoryResponseDto dto) {
    Link selfLink = linkTo(methodOn(ListingCategoryController.class).get(dto.getId())).withSelfRel();
    Link updateLink = linkTo(methodOn(ListingCategoryController.class).update(dto.getId(), null)).withRel("update");
    Link deleteLink = linkTo(methodOn(ListingCategoryController.class).delete(dto.getId())).withRel("delete");
    Link listLink = linkTo(methodOn(ListingCategoryController.class).getAll()).withRel("list");
    return dto.add(selfLink, updateLink, deleteLink, listLink);
  }

}
