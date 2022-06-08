package com.haginus.payment.mapper;

import com.haginus.common.clients.payment.dto.Transaction.TransactionResponseDto;
import com.haginus.payment.model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
  private final ModelMapper modelMapper;
  private final PaymentMethodMapper paymentMethodMapper;

  public TransactionMapper(PaymentMethodMapper paymentMethodMapper) {
    this.modelMapper = new ModelMapper();
    this.paymentMethodMapper = paymentMethodMapper;
  }

  public TransactionResponseDto toDto(Transaction entity) {
    TransactionResponseDto dto = this.modelMapper.map(entity, TransactionResponseDto.class);
    dto.setPaymentMethod(this.paymentMethodMapper.toDto(entity.getPaymentMethod()));
    return dto;
  }





}
