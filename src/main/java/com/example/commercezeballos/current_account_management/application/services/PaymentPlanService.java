package com.example.commercezeballos.current_account_management.application.services;
import com.example.commercezeballos.current_account_management.application.dtos.request.PaymentPlanRequestDto;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

public interface PaymentPlanService {
    ApiResponse<?> registerPaymentPlan(PaymentPlanRequestDto paymentPlanRequestDto);
    ApiResponse<?> findPaymentPlanByDni(String dni);
}
