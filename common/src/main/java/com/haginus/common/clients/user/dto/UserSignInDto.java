package com.haginus.common.clients.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserSignInDto {
  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  @Length(min = 6)
  private String password;
}
