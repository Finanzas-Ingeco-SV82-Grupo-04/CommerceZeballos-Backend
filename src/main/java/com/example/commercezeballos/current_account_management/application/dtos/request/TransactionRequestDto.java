package com.example.commercezeballos.current_account_management.application.dtos.request;


import com.example.commercezeballos.current_account_management.domain.enums.ETransactionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {

    @NotNull(message = "El monto de la transacción no puede ser nulo")
    private Double transactionAmountNotInterest;

    @NotNull(message = "El tipo de transacción no puede ser nulo")
    private Double transactionAmountWithInterest;

    @Valid
    @NotNull(message = "El tipo de transacción no puede ser nulo")
    private ETransactionType transactionType;

    //Puede ser nulo
    private String transactionDescription;

    @NotNull(message = "El pago completado no puede ser nulo")
    private Integer installments;// numero de cuotas

    @NotNull(message = "El monto por cuota no puede ser nulo")
    private Double installmentAmount;// monto por cuota

    @NotBlank(message = "El DNI del cliente no puede ser vacío")
    @NotNull(message = "El DNI del cliente no puede ser nulo")
    @Size(min = 8, max = 8, message = "El DNI del cliente debe tener 8 dígitos")
    @Pattern(regexp = "\\d{8}", message = "La fecha de pago debe tener exactamente 8 dígitos")
    private String dniClient;

    @NotNull(message = "La lista de productos no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos un producto en la lista")
    private List<Long> productsIds = new ArrayList<>();


}
