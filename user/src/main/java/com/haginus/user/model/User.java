package com.haginus.user.model;

import com.haginus.common.clients.user.dto.UserResponseDto;
import com.haginus.common.security.MyUserDetails;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "birth_date", nullable = false)
  private Date birthDate;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToOne(mappedBy = "user", orphanRemoval = true)
  private KYCProcess kycProcess;

  public UserResponseDto toDto() {
    return new ModelMapper().map(this, UserResponseDto.class);
  }

  public MyUserDetails toUserDetails() {
    return new ModelMapper().map(this, MyUserDetails.class);
  }

}
