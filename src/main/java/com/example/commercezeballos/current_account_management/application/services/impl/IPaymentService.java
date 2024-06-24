package com.example.commercezeballos.current_account_management.application.services.impl;

import com.example.commercezeballos.current_account_management.application.dtos.request.PaymentRequestDto;
import com.example.commercezeballos.current_account_management.application.dtos.response.TransactionResponseDto;
import com.example.commercezeballos.current_account_management.application.services.PaymentService;
import com.example.commercezeballos.current_account_management.domain.entities.Payment;
import com.example.commercezeballos.current_account_management.infraestructure.CurrentAccountRepository;
import com.example.commercezeballos.current_account_management.infraestructure.PaymentRepository;
import com.example.commercezeballos.current_account_management.infraestructure.TransactionRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IPaymentService implements PaymentService {

    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapperConfig modelMapperConfig;
    private final CurrentAccountRepository currentAccountRepository;

    public IPaymentService(TransactionRepository transactionRepository, PaymentRepository paymentRepository, ModelMapperConfig modelMapperConfig, CurrentAccountRepository currentAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.paymentRepository = paymentRepository;
        this.modelMapperConfig = modelMapperConfig;
        this.currentAccountRepository = currentAccountRepository;
    }

    // Implement methods
    @Override
    public ApiResponse<?> registerPayment(PaymentRequestDto paymentRequestDto) {
        var currentAccount = currentAccountRepository.findByDniClient(paymentRequestDto.getDniClient())
                .orElseThrow(() -> new ResourceNotFoundException("Current Account not found"));

        //imprimir las transacciones
       var transactionsByCurrentAccount = transactionRepository.findAllByCurrentAccountIdAndPaymentCompletedIsFalseAndTransactionDateBefore(currentAccount.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transactions not found"));

        //imprimir las transacciones


        var payment = modelMapperConfig.modelMapper().map(paymentRequestDto, Payment.class);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentAmount(12.0);//dato de prueba nms
        payment.setTransactions(transactionsByCurrentAccount);

        //registrar el pago
        paymentRepository.save(payment);

        return new ApiResponse<>(true, "Payment registered successfully", null);
    }

}
