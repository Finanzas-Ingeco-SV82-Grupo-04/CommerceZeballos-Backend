package com.example.commercezeballos.current_account_management.application.controllers;

import com.example.commercezeballos.current_account_management.application.dtos.request.PaymentPlanRequestDto;
import com.example.commercezeballos.current_account_management.application.services.PaymentPlanService;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment-plans")
public class PaymentsPlanController {
    private final PaymentPlanService paymentPlanService;

    @Autowired
    public PaymentsPlanController(PaymentPlanService paymentPlanService) {
        this.paymentPlanService = paymentPlanService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerPaymentPlan(@Valid @RequestBody PaymentPlanRequestDto paymentPlanRequestDto) {
        var response = paymentPlanService.registerPaymentPlan(paymentPlanRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{dni}")
    public ResponseEntity<ApiResponse<?>> findPaymentPlanByDni(@PathVariable String dni) {
        var response = paymentPlanService.findPaymentPlanByDni(dni);
        return ResponseEntity.ok(response);
    }
}
