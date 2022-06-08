package com.haginus.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
@EnableFeignClients(
  basePackages = "com.haginus.common.clients"
)
public class MarketplaceApplication {
  public static void main(String[] args) {
    SpringApplication.run(MarketplaceApplication.class, args);
  }
}
