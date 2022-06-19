package com.haginus.common.security.jwtutils;

import com.haginus.common.security.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Override
  public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return null;
  }
}
