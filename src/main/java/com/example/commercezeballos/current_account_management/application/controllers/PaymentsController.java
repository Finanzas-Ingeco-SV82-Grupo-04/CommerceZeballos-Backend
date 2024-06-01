package com.example.commercezeballos.current_account_management.application.controllers;


import com.example.commercezeballos.current_account_management.application.dtos.request.PaymentRequestDto;
import com.example.commercezeballos.current_account_management.application.services.PaymentService;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerPayment(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        var response = paymentService.registerPayment(paymentRequestDto);
        return ResponseEntity.ok(response);
    }
}
