package com.haginus.marketplace.config;

import com.haginus.common.security.FeignClientInterceptor;
import com.haginus.common.security.jwtutils.TokenIssuer;
import com.haginus.common.security.jwtutils.TokenManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  private final TokenManager tokenManager;

  @Bean
  FeignClientInterceptor feignClientInterceptor() {
    return new FeignClientInterceptor(TokenIssuer.PAYMENT_SERVICE, tokenManager);
  };
}
