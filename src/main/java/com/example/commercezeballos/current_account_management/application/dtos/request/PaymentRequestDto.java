package com.example.commercezeballos.current_account_management.application.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    @NotBlank(message = "El DNI del cliente no puede ser vacío")
    @NotNull(message = "El DNI del cliente no puede ser nulo")
    @Size(min = 8, max = 8, message = "El DNI del cliente debe tener 8 dígitos")
    @Pattern(regexp = "\\d{8}", message = "La fecha de pago debe tener exactamente 8 dígitos")
    private String dniClient;
}
