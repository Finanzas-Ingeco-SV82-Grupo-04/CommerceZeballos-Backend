package com.example.commercezeballos.current_account_management.application.dtos.request;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeFrecuency;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeCredit;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPlanRequestDto {
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto es el credito")
    private Double amountNotInterest;

    @NotNull
    private ETypeCredit transactionType;//americano o frances

    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "El DNI debe de ser de 8 dígitos")
    private String dniClient;

    @NotNull
    private ETypeFrecuency paymentFrequency;//semanal o quincenal

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "La tasa de interés semanal o quincenal debe ser mayor a 0")
    private Double interestRateByPaymentFrequency;


}
