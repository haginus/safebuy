package com.haginus.common.clients.payment;

import com.haginus.common.clients.payment.dto.Transaction.MarketplaceTransactionDto;
import com.haginus.common.clients.payment.dto.Transaction.TransactionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment", url = "${clients.payment.url}")
public interface PaymentClient {
  @PostMapping("/transactions/marketplace/sold")
  TransactionResponseDto marketplaceListingSold(@RequestBody MarketplaceTransactionDto dto);
}
