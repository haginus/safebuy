package com.haginus.user.controller;

import com.haginus.common.clients.user.dto.AuthResponse;
import com.haginus.common.clients.user.dto.UserResponseDto;
import com.haginus.common.clients.user.dto.UserSignInDto;
import com.haginus.common.clients.user.dto.UserSignUpDto;
import com.haginus.common.exception.ForbiddenException;
import com.haginus.common.security.AllowOnlyIssuer;
import com.haginus.common.security.MyUserDetails;
import com.haginus.common.security.jwtutils.TokenIssuer;
import com.haginus.user.mapper.UserMapper;
import com.haginus.user.model.User;
import com.haginus.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/auth/sign-up")
  ResponseEntity<AuthResponse> signUp(@Valid @RequestBody UserSignUpDto dto) {
    User user = this.userService.create(this.userMapper.toEntity(dto));
    AuthResponse authResponse = this.userService.signIn(user);
    return ResponseEntity.ok().body(authResponse);
  }

  @PostMapping("/auth/sign-in")
  ResponseEntity<AuthResponse> signIn(@Valid @RequestBody UserSignInDto dto) {
    AuthResponse authResponse = this.userService.signIn(dto.getEmail(), dto.getPassword());
    return ResponseEntity.ok().body(authResponse);
  }

  @AllowOnlyIssuer(TokenIssuer.USER_SERVICE_CLIENT)
  @GetMapping("/{userId}")
  ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId, Authentication authentication) {
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    if(!Objects.equals(userDetails.getId(), userId)) {
      throw new ForbiddenException();
    }
    User user = this.userService.get(userId);
    return ResponseEntity.ok().body(this.userMapper.toDto(user));
  }

}
