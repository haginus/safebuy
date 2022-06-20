package com.haginus.common.autoconfigure;

import com.haginus.common.security.SecurityAspect;
import com.haginus.common.advice.ExceptionHandling;
import com.haginus.common.security.SecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({ExceptionHandling.class})
public class CommonAutoConfiguration {
}
