package com.haginus.common.clients.user;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user")
public interface UserClient {
}
