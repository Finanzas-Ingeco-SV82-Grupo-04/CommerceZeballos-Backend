package com.example.commercezeballos.current_account_management.application.dtos.response;

import com.example.commercezeballos.current_account_management.domain.entities.Transaction;
import com.example.commercezeballos.current_account_management.domain.enums.EInterest;
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
    private LocalDate paymentDate;
    private LocalDateTime openingDate;
    private Integer paymentDay;
    private LocalDate accountClosingDate;
    private Double interestRate;
    private Double moratoriumRate;
    private String dniClient;

}
