package com.example.commercezeballos.current_account_management.application.controllers;

import com.example.commercezeballos.current_account_management.application.dtos.request.TransactionRequestDto;
import com.example.commercezeballos.current_account_management.application.services.TransactionService;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerTransaction(@Valid @RequestBody TransactionRequestDto transactionRequestDto) {
        var response = transactionService.registerTransaction(transactionRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/current-account/{currentAccountId}")
    public ResponseEntity<ApiResponse<?>> getAllTransactionsByCurrentAccountId(@PathVariable Long currentAccountId) {
        var response = transactionService.getAllTransactionsByCurrentAccountId(currentAccountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<?>> getTransactionById(@PathVariable Long transactionId) {
        var response = transactionService.getTransactionById(transactionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
