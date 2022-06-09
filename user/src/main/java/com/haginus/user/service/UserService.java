package com.haginus.user.service;

import com.haginus.common.clients.payment.PaymentClient;
import com.haginus.common.clients.user.dto.AuthResponse;
import com.haginus.common.exception.ResourceAlreadyExistsException;
import com.haginus.common.exception.ResourceNotFoundException;
import com.haginus.common.exception.ServiceCommunicationException;
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
  private final PaymentClient paymentClient;

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
      throw new AuthException("Email is already in use.");
    }
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    User result = this.userRepository.save(user);
    try {
      this.paymentClient.createAccount(result.getId());
      return result;
    } catch (Exception e) {
      this.userRepository.delete(result);
      throw new ServiceCommunicationException("There was a problem with creating the account.");
    }
  }

  public AuthResponse signIn(String email, String password) {
    User user = this.getByEmail(email);
    if(!this.passwordEncoder.matches(password, user.getPassword())) {
      throw new AuthException("Wrong password.");
    }
    return this.signIn(user);
  }

  public AuthResponse signIn(User user) {
    return new AuthResponse("token", user.toDto());
  }
}
