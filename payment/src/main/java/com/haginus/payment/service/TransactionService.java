package com.haginus.payment.service;

import com.haginus.common.clients.marketplace.MarketplaceClient;
import com.haginus.common.clients.marketplace.dto.Listing.ListingResponseDto;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferRequestDto;
import com.haginus.common.clients.marketplace.dto.ListingOffer.ListingOfferResponseDto;
import com.haginus.common.exception.ForbiddenException;
import com.haginus.common.exception.NotAllowedException;
import com.haginus.common.exception.ServiceCommunicationException;
import com.haginus.payment.config.PropertiesConfig;
import com.haginus.payment.exception.InsufficientFounds;
import com.haginus.payment.exception.InvalidPaymentMethod;
import com.haginus.payment.model.Account;
import com.haginus.payment.model.PaymentMethod;
import com.haginus.payment.model.Transaction;
import com.haginus.common.clients.payment.dto.Transaction.TransactionType;
import com.haginus.payment.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final PaymentMethodService paymentMethodService;
  private final AccountService accountService;
  private final PropertiesConfig propertiesConfig;
  private final MarketplaceClient marketplaceClient;

  public List<Transaction> getAllByAccountId(Long accountId) {
    return this.transactionRepository.findAllByAccount_UserId(accountId);
  }

  @Transactional
  public Transaction withdraw(Account account, PaymentMethod paymentMethod, Double amount) {
    if(amount <= 0) {
      throw new NotAllowedException("You cannot withdraw a negative amount.");
    }
    if(amount < propertiesConfig.getMinTransactionAmount()) {
      throw new NotAllowedException(String.format("Minimum amount is %f.", propertiesConfig.getMinTransactionAmount()));
    }
    if(paymentMethod.getId() != null && !Objects.equals(paymentMethod.getAccount().getUserId(), account.getUserId())) {
      throw new ForbiddenException("Payment method does not belong to selected account.");
    }
    if(account.getBalance() - amount < 0) {
      throw new NotAllowedException("Requested amount exceeds balance.");
    }
    Transaction transaction = Transaction.builder()
      .id(UUID.randomUUID().toString())
      .amount(-amount)
      .timestamp(new Timestamp(System.currentTimeMillis()))
      .type(TransactionType.WITHDRAW)
      .paymentMethod(paymentMethod)
      .account(account)
      .build();
    this.accountService.changeBalance(account, -amount);
    return this.transactionRepository.save(transaction);
  }

  public Transaction creditAccount(Account account, PaymentMethod paymentMethod, Double amount) {
    if(amount <= 0) {
      throw new NotAllowedException("You cannot credit a negative amount.");
    }
    if(amount < propertiesConfig.getMinTransactionAmount()) {
      throw new NotAllowedException(String.format("Minimum amount is %f.", propertiesConfig.getMinTransactionAmount()));
    }
    if(paymentMethod.getId() != null && !Objects.equals(paymentMethod.getAccount().getUserId(), account.getUserId())) {
      throw new ForbiddenException("Payment method does not belong to selected account.");
    }
    try {
      this.mockSwipeCall(paymentMethod, amount);
      this.paymentMethodService.safeSave(paymentMethod, account);
      Transaction transaction = Transaction.builder()
        .id(UUID.randomUUID().toString())
        .amount(amount)
        .timestamp(new Timestamp(System.currentTimeMillis()))
        .type(TransactionType.TOP_UP)
        .paymentMethod(paymentMethod)
        .account(account)
        .build();
      this.accountService.changeBalance(account, amount);
      return this.transactionRepository.save(transaction);
    } catch (InsufficientFounds e) {
      this.paymentMethodService.safeSave(paymentMethod, account);
      throw e;
    }
  }

  public Transaction buyMarketplaceListing(Long accountId, Long listingId, Double amount) {
    Transaction transaction = this.processMarketplaceTransaction(accountId, listingId, -amount, TransactionType.LISTING_BUY);
    try {
      ListingResponseDto listing = marketplaceClient.getOffer(listingId);
      if(Objects.equals(listing.getPrice(), amount)) {
        ListingOfferRequestDto dto = ListingOfferRequestDto.builder()
          .buyerId(accountId)
          .paymentId(transaction.getId())
          .build();
        marketplaceClient.createOffer(dto, listingId);
        return transaction;
      } else {
        transaction.setType(TransactionType.TOP_UP);
        this.transactionRepository.save(transaction);
        return transaction;
      }
    } catch (Exception e) {
      transaction.setType(TransactionType.TOP_UP);
      this.transactionRepository.save(transaction);
      throw new ServiceCommunicationException("Couldn't buy listing. Money went back to your account.");
    }
  }

  public Transaction marketplaceListingSold(Long accountId, Long listingId, Double amount) {
    return this.processMarketplaceTransaction(accountId, listingId, amount, TransactionType.LISTING_SELL);
  }

  public Transaction marketplaceListingRefunded(Long accountId, Long listingId, Double amount) {
    return this.processMarketplaceTransaction(accountId, listingId, amount, TransactionType.LISTING_REFUND);
  }

  private Transaction processMarketplaceTransaction(Long accountId, Long listingId, Double amount, TransactionType type) {
    Account account = this.accountService.get(accountId);
    Transaction transaction = Transaction.builder()
      .id(UUID.randomUUID().toString())
      .amount(amount)
      .timestamp(new Timestamp(System.currentTimeMillis()))
      .type(type)
      .paymentMethod(null)
      .details(String.format("{ listingId: \"%d\"}", listingId))
      .account(account)
      .build();
    boolean allowCrediting = type != TransactionType.LISTING_BUY;
    this.accountService.changeBalance(account, amount, allowCrediting);
    return this.transactionRepository.save(transaction);
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
