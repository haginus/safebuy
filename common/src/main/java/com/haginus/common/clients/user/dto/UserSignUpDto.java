package com.haginus.common.clients.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserSignUpDto {
  @NotEmpty
  private String firstName;

  @NotEmpty
  private String lastName;

  @NotNull
  @DateTimeFormat
  private Date birthDate;

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  @Length(min = 6)
  private String password;
}
