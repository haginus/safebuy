package com.haginus.marketplace.service;

import com.haginus.common.exception.ResourceAlreadyExistsException;
import com.haginus.common.exception.ResourceNotFoundException;
import com.haginus.marketplace.model.ListingCategory;
import com.haginus.marketplace.repository.ListingCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListingCategoryService {
  private final ListingCategoryRepository listingCategoryRepository;

  public List<ListingCategory> getAll() {
    return this.listingCategoryRepository.findAll();
  }

  public ListingCategory get(Long id) {
    Optional<ListingCategory> optional = this.listingCategoryRepository.findById(id);
    return optional.orElseThrow(() -> new ResourceNotFoundException("Listing category does not exist."));
  }

  public ListingCategory create(ListingCategory category) {
    if(category.getId() != null) {
      Optional<ListingCategory> optional = this.listingCategoryRepository.findById(category.getId());
      if(optional.isPresent()) {
        throw new ResourceAlreadyExistsException("Listing category already exists.");
      }
    }
    return this.listingCategoryRepository.save(category);
  }

  public ListingCategory update(ListingCategory listingCategory) {
    this.get(listingCategory.getId());
    return this.listingCategoryRepository.save(listingCategory);
  }

  public void delete(Long id) {
    ListingCategory listingCategory = this.get(id);
    this.listingCategoryRepository.delete(listingCategory);
  }
}
