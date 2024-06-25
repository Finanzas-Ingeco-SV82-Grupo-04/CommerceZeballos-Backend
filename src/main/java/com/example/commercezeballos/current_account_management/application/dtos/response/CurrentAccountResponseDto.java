package com.example.commercezeballos.current_account_management.application.dtos.response;

import com.example.commercezeballos.current_account_management.domain.entities.Transaction;
import com.example.commercezeballos.current_account_management.domain.enums.EInterest;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeFrecuency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccountResponseDto {
    private Long id;
    private EInterest typeInterest;
    private Double creditLimit;
    private Double usedCredit;
    private Integer numberOfMonths;
    private LocalDateTime openingDate;
    private ETypeFrecuency paymentFrequency;
    private Double interestRate;
    private Double moratoriumRate;
    private String dniClient;
}
