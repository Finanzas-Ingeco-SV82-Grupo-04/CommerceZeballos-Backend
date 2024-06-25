package com.example.commercezeballos.current_account_management.application.dtos.response;

import com.example.commercezeballos.current_account_management.domain.enums.ETypeCredit;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeFrecuency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPlanResponseDto {
    private Long id;
    private Double paymentAmount;
    private String paymentDate;
    private Long currentAccountId;
    private Long transactionId;
    private ETypeFrecuency paymentFrequency;
    private ETypeCredit creditType;
    private Double interestRateByPaymentFrequency;
    private Double amountPerPaymentPlan;
    private LocalDate startDate;

    private Boolean isPaid;
}
