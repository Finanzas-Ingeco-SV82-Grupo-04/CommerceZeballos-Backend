package com.example.commercezeballos.current_account_management.application.dtos.request;


import com.example.commercezeballos.current_account_management.domain.enums.EInterest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCurrentAccountRequestDto {
    @Valid
    @NotNull(message = "El tipo de interés no puede ser nulo")
    private EInterest typeInterest;

    @NotNull(message = "El límite de crédito no puede ser nulo")
    private Double creditLimit;

    @NotNull(message = "La fecha de pago no puede ser nula")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @NotNull(message = "La fecha de cierre de cuenta no puede ser nula")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate accountClosingDate;

    @NotNull(message = "La tasa de interés no puede ser nula")
    private Double interestRate;

    @NotNull(message = "La tasa de mora no puede ser nula")
    private Double moratoriumRate;
}
