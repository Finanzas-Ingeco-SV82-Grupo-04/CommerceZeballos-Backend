package com.example.commercezeballos.current_account_management.infraestructure;
import com.example.commercezeballos.current_account_management.domain.entities.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {

    Optional<PaymentPlan> findByCurrentAccount_DniClient(String dniClient);

    //traer la lista de planes de pago por dni del cliente

    List<PaymentPlan> findAllByCurrentAccount_DniClient(String dniClient);


    //delete
    void deleteByCurrentAccount_DniClient(String dni);


    //actulaizar el estado de pago de un plan de pago por id
    @Modifying
    @Transactional
    @Query("UPDATE PaymentPlan p SET p.isPaid = :isPaid WHERE p.id = :id")
    void updatePaymentPlanIsPaidById(Long id, Boolean isPaid);


    //traer los planes de pago por dni del cliente
    @Query("SELECT p FROM PaymentPlan p WHERE p.currentAccount.dniClient = :dniClient")
    List<PaymentPlan> findPaymentPlanByDni(String dniClient);
}
