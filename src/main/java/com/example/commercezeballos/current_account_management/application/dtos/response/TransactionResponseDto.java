package com.example.commercezeballos.current_account_management.application.dtos.response;

import com.example.commercezeballos.current_account_management.domain.enums.ETransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private Long id;
    private Double transactionAmountNotInterest;
    private Double transactionAmountWithInterest;
    private ETransactionType transactionType;
    private String transactionDescription;
    private Integer installments;// numero de cuotas
    private Double installmentAmount;// monto por cuota
    private String transactionDate;
    private Long currentAccountId;
    private List<Long> productIds = new ArrayList<>();


}
