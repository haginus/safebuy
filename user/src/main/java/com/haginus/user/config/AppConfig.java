package com.haginus.user.config;

import com.haginus.common.security.FeignClientInterceptor;
import com.haginus.common.security.jwtutils.TokenIssuer;
import com.haginus.common.security.jwtutils.TokenManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AppConfig {

  private final TokenManager tokenManager;

  @Bean
  FeignClientInterceptor feignClientInterceptor() {
    return new FeignClientInterceptor(TokenIssuer.USER_SERVICE, tokenManager);
  };
}
