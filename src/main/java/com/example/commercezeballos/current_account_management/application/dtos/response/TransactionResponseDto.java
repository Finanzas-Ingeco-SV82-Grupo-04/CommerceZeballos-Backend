package com.example.commercezeballos.current_account_management.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {
    private Long id;
    private Double transactionAmountNotInterest;
    private String transactionDescription;
    private String transactionDate;
    private Long currentAccountId;
    private List<Long> productIds = new ArrayList<>();
}
