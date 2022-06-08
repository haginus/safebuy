package com.haginus.common.clients.user.dto;

import lombok.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDto {
  private Long id;
  private String firstName;
  private String lastName;
  private Date birthDate;
  private String email;
}
