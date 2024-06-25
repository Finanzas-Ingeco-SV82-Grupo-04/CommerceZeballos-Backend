package com.example.commercezeballos.current_account_management.application.services;
import com.example.commercezeballos.current_account_management.application.dtos.request.PaymentPlanRequestDto;
import com.example.commercezeballos.current_account_management.domain.entities.CurrentAccount;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

public interface PaymentPlanService {
    ///ApiResponse<?> registerPaymentPlan(PaymentPlanRequestDto paymentPlanRequestDto);
    ApiResponse<?> findPaymentPlanByDni(String dni);

    void registerPaymentPlanByNumberPayments(Integer countInstallment, String dniClient);

    void updatePaymentPlans(CurrentAccount currentAccount);

    //actualizar el estado de pago de un plan de pago por id
    void updatePaymentPlanIsPaidById(Long id, Boolean isPaid);

    //traer los planes de pago por dni del cliente
}
