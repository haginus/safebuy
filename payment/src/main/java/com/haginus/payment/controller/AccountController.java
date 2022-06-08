package com.haginus.payment.controller;

import com.haginus.common.clients.payment.dto.AccountDto;
import com.haginus.payment.mapper.AccountMapper;
import com.haginus.payment.model.Account;
import com.haginus.payment.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

  private final AccountService accountService;
  private final AccountMapper accountMapper;

  @GetMapping("/{id}")
  ResponseEntity<AccountDto> get(@PathVariable Long id) {
    Account account = this.accountService.get(id);
    return ResponseEntity.ok().body(this.accountMapper.toDto(account));
  }

  @PostMapping("/{id}")
  ResponseEntity<AccountDto> create(@PathVariable Long id) {
    Account account = this.accountService.create(id);
    return ResponseEntity.ok().body(this.accountMapper.toDto(account));
  }
}
