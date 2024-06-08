package com.example.commercezeballos.current_account_management.application.dtos.request;

import com.example.commercezeballos.current_account_management.domain.enums.EInterest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAccountRequestDto {

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

    @NotNull(message = "El día de pago no puede ser nulo")
    private Integer paymentDay;

    @NotNull(message = "La tasa de interés no puede ser nula")
    private Double interestRate;

    @NotNull(message = "La tasa de mora no puede ser nula")
    private Double moratoriumRate;

    @NotBlank(message = "El DNI del cliente no puede ser vacío")
    @NotNull(message = "El DNI del cliente no puede ser nulo")
    @Size(min = 8, max = 8, message = "El DNI del cliente debe tener 8 dígitos")
    @Pattern(regexp = "\\d{8}", message = "La fecha de pago debe tener exactamente 8 dígitos")

    private String dniClient;

}
