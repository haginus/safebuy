package com.haginus.payment.service;

import com.haginus.common.exception.NotAllowedException;
import com.haginus.common.exception.ResourceAlreadyExistsException;
import com.haginus.common.exception.ResourceNotFoundException;
import com.haginus.payment.model.Account;
import com.haginus.payment.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  public Account get(Long userId) {
    return this.accountRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("Account does not exist."));
  }

  public Account create(Long userId) {
    if(this.accountRepository.findById(userId).isPresent()) {
      throw new ResourceAlreadyExistsException("Account already exists for this user.");
    }
    Account account = Account.builder()
      .userId(userId)
      .balance(0.0)
      .build();
    return this.accountRepository.save(account);
  }

  public Account changeBalance(Long accountId, Long amount, boolean allowCrediting) {
    Account account = this.get(accountId);
    return this.changeBalance(account, amount, allowCrediting);
  }

  public Account changeBalance(Long accountId, Long amount) {
    return this.changeBalance(accountId, amount, false);
  }

  public Account changeBalance(Account account, Long amount, boolean allowCrediting) {
    double newBalance = account.getBalance() + amount;
    if(newBalance < 0 && !allowCrediting) {
      throw new NotAllowedException("There is not enough money.");
    }
    account.setBalance(newBalance);
    return this.accountRepository.save(account);
  }

  public Account changeBalance(Account account, Long amount) {
    return this.changeBalance(account, amount, false);
  }
}
