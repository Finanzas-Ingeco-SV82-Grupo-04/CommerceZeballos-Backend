package com.example.commercezeballos.current_account_management.infraestructure;

import com.example.commercezeballos.current_account_management.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
