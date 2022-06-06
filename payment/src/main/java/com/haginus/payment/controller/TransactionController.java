package com.haginus.payment.controller;

import com.haginus.payment.dto.Transaction.TopUpTransactionDto;
import com.haginus.payment.dto.Transaction.TransactionResponseDto;
import com.haginus.payment.mapper.PaymentMethodMapper;
import com.haginus.payment.mapper.TransactionMapper;
import com.haginus.payment.model.Account;
import com.haginus.payment.model.PaymentMethod;
import com.haginus.payment.model.Transaction;
import com.haginus.payment.service.AccountService;
import com.haginus.payment.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;
  private final AccountService accountService;
  private final TransactionMapper transactionMapper;
  private final PaymentMethodMapper paymentMethodMapper;

  @GetMapping("/account/{id}")
  public ResponseEntity<List<TransactionResponseDto>> getAllByAccountId(@PathVariable Long id) {
    return ResponseEntity.ok().body(
      this.transactionService.getAllByAccountId(id).stream()
        .map(this.transactionMapper::toDto)
        .collect(Collectors.toList())
    );
  }

  // TODO: Change mapping after adding security
  @PostMapping("/top-up/{id}")
  public ResponseEntity<TransactionResponseDto> topUp(@PathVariable Long id, @Valid @RequestBody TopUpTransactionDto dto) {
    Account account = this.accountService.get(id);
    PaymentMethod paymentMethod = this.paymentMethodMapper.toEntity(dto.getPaymentMethod());
    Transaction transaction = this.transactionService.creditAccount(account, paymentMethod, dto.getAmount());
    return ResponseEntity.ok().body(this.transactionMapper.toDto(transaction));
  }

  // TODO: Change mapping after adding security
  @PostMapping("/withdraw/{id}")
  public ResponseEntity<TransactionResponseDto> withdraw(@PathVariable Long id, @Valid @RequestBody TopUpTransactionDto dto) {
    Account account = this.accountService.get(id);
    PaymentMethod paymentMethod = this.paymentMethodMapper.toEntity(dto.getPaymentMethod());
    Transaction transaction = this.transactionService.withdraw(account, paymentMethod, dto.getAmount());
    return ResponseEntity.ok().body(this.transactionMapper.toDto(transaction));
  }
}
