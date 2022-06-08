package com.haginus.common.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@AutoConfiguration
@PropertySources({
  @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class ClientsAutoConfiguration {
  ClientsAutoConfiguration() {

  }
}
