package com.haginus.common.autoconfigure;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@AutoConfiguration
@PropertySources({
  @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
@Log4j2
public class ClientsAutoConfiguration {
  ClientsAutoConfiguration() {
    log.info("Clients auto configuration inserted.");
  }
}
