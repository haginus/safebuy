package com.haginus.payment.mapper;

import com.haginus.payment.dto.PaymentMethodRequestDto;
import com.haginus.payment.dto.PaymentMethodResponseDto;
import com.haginus.payment.model.PaymentMethod;
import com.haginus.payment.service.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {
  private final ModelMapper modelMapper;
  private final PaymentMethodService paymentMethodService;

  public PaymentMethodMapper(PaymentMethodService paymentMethodService) {
    this.modelMapper = new ModelMapper();
    this.paymentMethodService = paymentMethodService;
  }

  public PaymentMethodResponseDto toDto(PaymentMethod entity) {
    if(entity == null) return null;
    PaymentMethodResponseDto dto = this.modelMapper.map(entity, PaymentMethodResponseDto.class);
    dto.setCardNumber("************" + entity.getCardNumber().substring(12));
    return dto;
  }

  public PaymentMethod toEntity(PaymentMethodRequestDto dto) {
    if(dto.getId() != null) {
      return this.paymentMethodService.get(dto.getId());
    }
    return this.modelMapper.map(dto, PaymentMethod.class);
  }





}
