package com.example.commercezeballos.current_account_management.application.dtos.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentAmountRequestDto {
    private Integer installmentId;
    private Double amount;
}

