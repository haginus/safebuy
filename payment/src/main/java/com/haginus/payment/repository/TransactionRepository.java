package com.haginus.payment.repository;

import com.haginus.payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
  List<Transaction> findAllByAccount_UserId(Long accountId);
}
