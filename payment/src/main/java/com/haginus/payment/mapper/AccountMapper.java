package com.haginus.payment.mapper;

import com.haginus.payment.dto.AccountDto;
import com.haginus.payment.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AccountMapper {
  private final ModelMapper modelMapper;
  private final PaymentMethodMapper paymentMethodMapper;

  public AccountMapper(PaymentMethodMapper paymentMethodMapper) {
    this.modelMapper = new ModelMapper();
    this.paymentMethodMapper = paymentMethodMapper;
  }

  public AccountDto toDto(Account entity) {
    AccountDto dto = this.modelMapper.map(entity, AccountDto.class);
    dto.setPaymentMethods(
      entity.getPaymentMethods().stream().map(this.paymentMethodMapper::toDto).collect(Collectors.toList())
    );
    return dto;
  }
}
