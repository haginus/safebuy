package com.haginus.payment.service;

import com.haginus.common.exception.ForbiddenException;
import com.haginus.common.exception.NotAllowedException;
import com.haginus.payment.exception.InsufficientFounds;
import com.haginus.payment.exception.InvalidPaymentMethod;
import com.haginus.payment.model.Account;
import com.haginus.payment.model.PaymentMethod;
import com.haginus.payment.model.Transaction;
import com.haginus.payment.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final PaymentMethodService paymentMethodService;

  public Transaction withdraw(Account account, PaymentMethod paymentMethod, Double amount, Long userId) {
    if(amount <= 0) {
      throw new NotAllowedException("You cannot withdraw a negative amount.");
    }
    if(userId != null && !Objects.equals(account.getUserId(), userId)) {
      throw new ForbiddenException();
    }
    if(!Objects.equals(paymentMethod.getAccount().getUserId(), account.getUserId())) {
      throw new ForbiddenException("Payment method does not belong to selected account.");
    }
    if(account.getBalance() - amount < 0) {
      throw new NotAllowedException("Requested amount exceeds balance.");
    }
    Transaction transaction = Transaction.builder()
      .id(UUID.randomUUID().toString())
      .amount(-amount)
      .timestamp(new Timestamp(System.currentTimeMillis()))
      .paymentMethod(paymentMethod)
      .account(account)
      .build();
    return this.transactionRepository.save(transaction);
  }

  public Transaction creditAccount(Account account, PaymentMethod paymentMethod, Double amount, Long userId) {
    if(amount <= 0) {
      throw new NotAllowedException("You cannot credit a negative amount.");
    }
    if(userId != null && !Objects.equals(account.getUserId(), userId)) {
      throw new ForbiddenException();
    }
    try {
      this.mockSwipeCall(paymentMethod, amount);
      this.paymentMethodService.safeSave(paymentMethod, account);
      Transaction transaction = Transaction.builder()
        .id(UUID.randomUUID().toString())
        .amount(amount)
        .timestamp(new Timestamp(System.currentTimeMillis()))
        .paymentMethod(paymentMethod)
        .account(account)
        .build();
      return this.transactionRepository.save(transaction);
    } catch (InsufficientFounds e) {
      this.paymentMethodService.safeSave(paymentMethod, account);
      throw e;
    }
  }

  private void mockSwipeCall(PaymentMethod paymentMethod, Double amount) {
    if(paymentMethod.getCardNumber().startsWith("10")) {
      throw new InvalidPaymentMethod();
    }
    double cardAmount = Double.parseDouble(paymentMethod.getCardNumber().substring(12));
    if(cardAmount < amount) {
      throw new InsufficientFounds();
    }
  }
}
