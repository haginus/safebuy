package com.haginus.common.security;

import com.haginus.common.security.jwtutils.TokenIssuer;
import com.haginus.common.security.jwtutils.TokenManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class FeignClientInterceptor implements RequestInterceptor {

  private final TokenIssuer issuer;

  private final TokenManager tokenManager;

  public FeignClientInterceptor(TokenIssuer issuer, TokenManager tokenManager) {
    this.issuer = issuer;
    this.tokenManager = tokenManager;
  }

  @Override
  public void apply(RequestTemplate requestTemplate) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication == null) return;
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    String token = this.tokenManager.generateJwtToken(userDetails, this.issuer);
    String headerValue = "Bearer " + token;
    requestTemplate.header("Authorization", headerValue);
  }
}
