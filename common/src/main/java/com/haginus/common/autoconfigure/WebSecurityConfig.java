package com.haginus.common.autoconfigure;

import com.haginus.common.security.SecurityAspect;
import com.haginus.common.security.SecurityProperties;
import com.haginus.common.security.jwtutils.JwtAuthenticationEntryPoint;
import com.haginus.common.security.jwtutils.JwtFilter;
import com.haginus.common.security.jwtutils.JwtUserDetailsService;
import com.haginus.common.security.jwtutils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AutoConfiguration
@Import({
  JwtAuthenticationEntryPoint.class,
  JwtUserDetailsService.class,
  JwtFilter.class,
  TokenManager.class,
  SecurityAspect.class
})
@EnableConfigurationProperties(SecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private JwtAuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtFilter filter;

  @Autowired
  SecurityProperties securityProperties;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws
    Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if(securityProperties.getSecret() == null) {
      http.csrf().disable().authorizeRequests().anyRequest().permitAll();
      return;
    }
    http.csrf().disable()
      .authorizeRequests().antMatchers("/auth/**").permitAll()
      .anyRequest().authenticated()
      .and()
      .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }
}
