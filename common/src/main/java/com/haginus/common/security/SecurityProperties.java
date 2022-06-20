package com.haginus.common.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("common.security")
public class SecurityProperties {
  private String secret;
  private boolean ignoreIssuer = false;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public boolean getIgnoreIssuer() {
    return ignoreIssuer;
  }

  public void setIgnoreIssuer(boolean ignoreIssuer) {
    this.ignoreIssuer = ignoreIssuer;
  }
}
