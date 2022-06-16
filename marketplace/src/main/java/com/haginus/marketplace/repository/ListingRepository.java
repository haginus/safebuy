package com.haginus.marketplace.repository;

import com.haginus.marketplace.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
  @Query("select l from Listing l left join l.listingOffer lo where l.ownerId = :usedId or lo.buyerId = :usedId")
  public List<Listing> findAllByOwnerOrBuyerId(@Param("usedId") Long usedId);

  @Query("select l from Listing l where lower(l.title) like lower(concat('%', :search,'%')) or lower(l.description) like lower(concat('%', :search,'%'))")
  public List<Listing> findAllByTitleOrDescription(@Param("search") String search);

  @Query(value = "select l from Listing l where (lower(l.title) like lower(concat('%', :search,'%')) or lower(l.description) like lower(concat('%', :search,'%'))) and l.listingCategory.id = :categoryId")
  public List<Listing> findAllBySearchAndCategory(@Param("search") String search, @Param("categoryId") Long categoryId);

  public List<Listing> findAllByListingCategory_Id(Long listingCategory_id);

}
