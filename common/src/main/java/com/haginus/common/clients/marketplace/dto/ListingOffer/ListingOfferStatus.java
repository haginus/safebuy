package com.haginus.common.clients.marketplace.dto.ListingOffer;

public enum ListingOfferStatus {
  PENDING_SELLER_ACTION(),
  PENDING_BUYER_CONFIRMATION(),
  PENDING_DISPUTE(),
  ACCEPTED_BY_BUYER(true),
  ACCEPTED_BY_SYSTEM(true),
  ACCEPTED_BY_DISPUTE(true),
  REFUNDED_BY_DISPUTE(false);

  private boolean isPending;
  private boolean isAccepted;

  ListingOfferStatus() {
  }

  ListingOfferStatus(boolean isAccepted) {
    this.isPending = false;
    this.isAccepted = isAccepted;
  }

  public boolean isPending() {
    return isPending;
  }

  public boolean isAccepted() {
    return isAccepted;
  }

  public boolean isDeclined() {
    return !isPending && !isAccepted;
  }
}
