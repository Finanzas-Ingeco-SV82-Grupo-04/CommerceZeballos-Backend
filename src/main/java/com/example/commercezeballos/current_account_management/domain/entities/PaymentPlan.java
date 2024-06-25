package com.example.commercezeballos.current_account_management.domain.entities;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeFrecuency;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeCredit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_plans")
public class PaymentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "current_account_id")
    private CurrentAccount currentAccount;

    @Column(name = "amount_used_credit")
    private Double creditUsedOfCurrentAccount;//monto del credito usado de la cuenta corriente

    @Column(name = "amount_per_payment_plan")
    private Double amountPerPaymentPlan;

    @Enumerated(EnumType.STRING)
    private ETypeCredit creditType;

    @Enumerated(EnumType.STRING)
    private ETypeFrecuency paymentFrequency;

    private Double interestRateByPaymentFrequency;

    private LocalDate startDate;

    private Boolean isPaid;
}
