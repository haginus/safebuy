package com.haginus.common.clients.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponse {
  String token;
  UserResponseDto user;
}
