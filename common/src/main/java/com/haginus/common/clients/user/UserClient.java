package com.haginus.common.clients.user;

import com.haginus.common.clients.user.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", url = "${clients.user.url:#{null}}")
public interface UserClient {
  @GetMapping("{id}")
  UserResponseDto getUser(@PathVariable("id") Long id);
}
