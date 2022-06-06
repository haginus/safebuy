package com.haginus.payment.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;


@Data
public class PaymentMethodRequestDto {
  private Long id;

  @Length(min = 3)
  private String ownerName;

  @Length(min = 16, max = 16)
  private String cardNumber;

  @Pattern(regexp = "(0[1-9]|11|12)\\/(0[1-9]|[1-9][0-9])")
  private String expiration;

  @Length(min = 3, max = 3)
  private String cvv;

  @AssertTrue(message = "Please provide an existing method or a new one.")
  boolean isValid() {
    return id != null || (ownerName != null && cardNumber != null && expiration != null && cvv != null);
  }
}
