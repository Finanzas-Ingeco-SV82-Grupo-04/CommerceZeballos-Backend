package com.example.commercezeballos.current_account_management.infraestructure;
import com.example.commercezeballos.current_account_management.domain.entities.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
    Optional<PaymentPlan> findByCurrentAccount_DniClient(String dniClient);
}
