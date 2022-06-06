package com.haginus.payment.service;

import com.haginus.common.exception.ResourceAlreadyExistsException;
import com.haginus.common.exception.ResourceNotFoundException;
import com.haginus.payment.model.Account;
import com.haginus.payment.model.PaymentMethod;
import com.haginus.payment.repository.PaymentMethodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentMethodService {

  private final PaymentMethodRepository paymentMethodRepository;

  PaymentMethod get(Long id) {
    Optional<PaymentMethod> optional = this.paymentMethodRepository.findById(id);
    return optional.orElseThrow(() -> new ResourceNotFoundException("Payment method does not exist."));
  }

  PaymentMethod save(PaymentMethod paymentMethod, Account account) {
    if(paymentMethod.getId() != null) {
      Optional<PaymentMethod> optional = this.paymentMethodRepository.findById(paymentMethod.getId());
      if(optional.isPresent()) {
        throw new ResourceAlreadyExistsException("Payment method already exists.");
      }
    }
    paymentMethod.setAccount(account);
    return this.paymentMethodRepository.save(paymentMethod);
  }

  PaymentMethod safeSave(PaymentMethod paymentMethod, Account account) {
    if(paymentMethod.getId() != null) {
      return this.get(paymentMethod.getId());
    }
    paymentMethod.setAccount(account);
    return this.paymentMethodRepository.save(paymentMethod);
  }
}
