package com.example.commercezeballos.current_account_management.infraestructure;

import com.example.commercezeballos.current_account_management.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Optional<List<Transaction>> findAllByCurrentAccountId(Long currentAccountId);
}
