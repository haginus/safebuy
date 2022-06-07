package com.haginus.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("payment")
@Data
public class PropertiesConfig {
  private Double minTransactionAmount = 0.0;
}
