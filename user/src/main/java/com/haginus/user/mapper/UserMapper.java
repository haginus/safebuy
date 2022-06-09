package com.haginus.user.mapper;

import com.haginus.common.clients.user.dto.UserResponseDto;
import com.haginus.common.clients.user.dto.UserSignUpDto;
import com.haginus.user.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  private final ModelMapper modelMapper;

  public UserMapper() {
    this.modelMapper = new ModelMapper();
  }

  public User toEntity(UserSignUpDto dto) {
    return this.modelMapper.map(dto, User.class);
  }

  public UserResponseDto toDto(User entity) {
    return entity.toDto();
  }
}
