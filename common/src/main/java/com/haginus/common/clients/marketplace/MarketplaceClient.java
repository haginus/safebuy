package com.haginus.common.clients.marketplace;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "marketplace", url = "${clients.marketplace.url:#{null}}")
public interface MarketplaceClient {
}
