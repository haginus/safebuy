package com.haginus.user.service;

import com.haginus.common.exception.ResourceAlreadyExistsException;
import com.haginus.common.exception.ResourceNotFoundException;
import com.haginus.user.exception.AuthException;
import com.haginus.user.model.User;
import com.haginus.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User get(Long id) {
    Optional<User> optional = this.userRepository.findById(id);
    return optional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
  }

  public User getByEmail(String email) {
    Optional<User> optional = this.userRepository.findByEmail(email);
    return optional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
  }

  public User create(User user) {
    if(user.getId() != null && this.userRepository.findById(user.getId()).isPresent()) {
      throw new ResourceAlreadyExistsException("User already exists.");
    }
    if(this.userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new ResourceAlreadyExistsException("User already exists.");
    }
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    return this.userRepository.save(user);
  }

  public User checkCredentials(String email, String password) {
    User user = this.getByEmail(email);
    if(!this.passwordEncoder.matches(password, user.getPassword())) {
      throw new AuthException("Wrong password.");
    }
    return user;
  }
}
