package com.haginus.common.security;

import com.haginus.common.exception.ForbiddenException;
import com.haginus.common.security.jwtutils.TokenIssuer;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

@Aspect
public class SecurityAspect {

  @Before("@annotation(allowOnlyIssuer)")
  public void beforeAdvice(AllowOnlyIssuer allowOnlyIssuer) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

    List<TokenIssuer> acceptedIssuers = Arrays.asList(allowOnlyIssuer.value());
    if(!acceptedIssuers.contains(userDetails.getIssuer())) {
      throw new ForbiddenException();
    }
  }
}
