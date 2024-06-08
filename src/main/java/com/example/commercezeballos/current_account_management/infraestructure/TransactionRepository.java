package com.example.commercezeballos.current_account_management.infraestructure;

import com.example.commercezeballos.current_account_management.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Optional<List<Transaction>> findAllByCurrentAccountId(Long currentAccountId);

    //find transactions by current account id and paymentCompleted is false and transactionDate is before today
    @Query("SELECT t FROM Transaction t WHERE t.currentAccount.id = :currentAccountId AND t.paymentCompleted = false AND FUNCTION('DATE', t.transactionDate) < :paymentDate")
    Optional<List<Transaction>> findAllByCurrentAccountIdAndPaymentCompletedIsFalseAndTransactionDateBefore(
            @Param("currentAccountId") Long currentAccountId,
            @Param("paymentDate") LocalDate paymentDate);


}
