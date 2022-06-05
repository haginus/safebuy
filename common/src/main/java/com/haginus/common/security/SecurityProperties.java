package com.haginus.common.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("common.security")
public class SecurityProperties {
  private String secret;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }
}
