package com.haginus.common.clients.marketplace;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "marketplace")
public interface MarketplaceClient {
}