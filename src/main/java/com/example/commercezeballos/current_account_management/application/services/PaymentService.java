package com.example.commercezeballos.current_account_management.application.services;

import com.example.commercezeballos.current_account_management.application.dtos.request.PaymentRequestDto;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;

public interface PaymentService {

    ApiResponse<?> registerPayment(PaymentRequestDto paymentRequestDto);
}
