package com.haginus.common.clients.payment.dto.Transaction;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketplaceTransactionDto {
  @NotNull
  private Long accountId;

  @NotNull
  private Long listingId;

  @NotNull
  private Double amount;
}
