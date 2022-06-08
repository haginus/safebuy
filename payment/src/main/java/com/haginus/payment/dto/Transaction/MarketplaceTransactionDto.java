package com.haginus.payment.dto.Transaction;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MarketplaceTransactionDto {
  @NotNull
  private Long accountId;

  @NotNull
  private Long listingId;

  @NotNull
  private Double amount;
}
