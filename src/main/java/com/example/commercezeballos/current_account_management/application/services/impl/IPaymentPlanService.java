package com.example.commercezeballos.current_account_management.application.services.impl;
import com.example.commercezeballos.current_account_management.application.dtos.request.PaymentPlanRequestDto;
import com.example.commercezeballos.current_account_management.application.services.AccountService;
import com.example.commercezeballos.current_account_management.application.services.PaymentPlanService;
import com.example.commercezeballos.current_account_management.domain.entities.PaymentPlan;
import com.example.commercezeballos.current_account_management.infraestructure.CurrentAccountRepository;
import com.example.commercezeballos.current_account_management.infraestructure.PaymentPlanRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IPaymentPlanService implements PaymentPlanService {
    private final PaymentPlanRepository paymentPlanRepository;
    private final CurrentAccountRepository currentAccountRepository;
    private final ModelMapperConfig modelMapperConfig;
    private final AccountService accountService;

    public IPaymentPlanService(
            PaymentPlanRepository paymentPlanRepository,
            CurrentAccountRepository currentAccountRepository,
            ModelMapperConfig modelMapperConfig,
            AccountService accountService) {
        this.paymentPlanRepository = paymentPlanRepository;
        this.currentAccountRepository = currentAccountRepository;
        this.modelMapperConfig = modelMapperConfig;
        this.accountService = accountService;
    }

    @Override
    public ApiResponse<?> registerPaymentPlan(PaymentPlanRequestDto paymentPlanRequestDto) {
        var currentAccount = currentAccountRepository.findByDniClient(paymentPlanRequestDto.getDniClient())
                .orElseThrow(() -> new ResourceNotFoundException("Current Account not found"));

        var paymentPlan = modelMapperConfig.modelMapper().map(paymentPlanRequestDto, PaymentPlan.class);
        paymentPlan.setCurrentAccount(currentAccount);

        paymentPlanRepository.save(paymentPlan);

        return new ApiResponse<>(true, "Payment plan registered successfully", null);
    }

    @Override
    public ApiResponse<?> findPaymentPlanByDni(String dni) {
        Optional<PaymentPlan> paymentPlan = paymentPlanRepository.findByCurrentAccount_DniClient(dni);
        if (paymentPlan.isPresent()) {
            return new ApiResponse<>(true, "Payment plan found", paymentPlan.get());
        } else {
            throw new ResourceNotFoundException("Payment plan not found for DNI: " + dni);
        }
    }
}
