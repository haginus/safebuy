package com.haginus.marketplace.repository;

import com.haginus.marketplace.model.Listing;
import com.haginus.marketplace.model.ListingCategory;
import com.haginus.marketplace.model.ListingOffer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;

public final class ListingSpecifications {
  public static Specification<Listing> titleOrDescriptionContains(String expression) {
    return (root, query, builder) -> builder.or(
      builder.like(builder.lower(root.get("title")), contains(expression)),
      builder.like(builder.lower(root.get("description")), contains(expression))
    );
  }

  public static Specification<Listing> inCategory(Long categoryId) {
    return (root, query, builder) -> {
      Join<ListingCategory, Listing> category = root.join("listingCategory");
      return builder.equal(category.get("id"), categoryId);
    };
  }

  public static Specification<Listing> isAvailable(boolean isAvailable) {
    return (root, query, builder) -> {
      Subquery<Long> subquery = query.subquery(Long.class);
      Root<ListingOffer> subroot = subquery.from(ListingOffer.class);
      Join<Listing, ListingOffer> listing = subroot.join("listing");
      int ct = isAvailable ? 0 : 1;
      return builder.equal(subquery.select(builder.count(subroot)).where(builder.equal(listing.get("id"), root.get("id"))), ct);
    };
  }


  private static String contains(String expression) {
    return MessageFormat.format("%{0}%", expression.toLowerCase(Locale.ROOT));
  }

}
