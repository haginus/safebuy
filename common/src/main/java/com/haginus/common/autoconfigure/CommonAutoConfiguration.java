package com.haginus.common.autoconfigure;

import com.haginus.common.security.SecurityAspect;
import com.haginus.common.security.SecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({SecurityAspect.class})
@EnableConfigurationProperties(SecurityProperties.class)
public class CommonAutoConfiguration {
  private final SecurityProperties securityProperties;
  CommonAutoConfiguration(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
    System.out.println(securityProperties.getSecret());
  }
}
