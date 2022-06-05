package com.haginus.common.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SecurityAspect {
  @Pointcut("@annotation(com.haginus.common.security.AllowOnly)")
  private void selectMethods() {}

  @Before("selectMethods()")
  public void beforeAdvice(){
    System.out.println("Going to setup student profile!!!!!!!");
  }
}
