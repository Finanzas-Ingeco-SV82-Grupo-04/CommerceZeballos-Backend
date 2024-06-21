package com.example.commercezeballos.current_account_management.application.services;

import com.example.commercezeballos.current_account_management.application.dtos.request.TransactionRequestDto;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;

public interface TransactionService {

    ApiResponse<?> registerTransaction(TransactionRequestDto transactionRequestDto);

    ApiResponse<?> getAllTransactionsByCurrentAccountId(Long currentAccountId);

    ApiResponse<?> getTransactionById(Long transactionId);
}
