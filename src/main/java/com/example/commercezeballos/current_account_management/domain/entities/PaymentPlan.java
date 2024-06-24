package com.example.commercezeballos.current_account_management.domain.entities;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeDescription;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeFrecuency;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private Double amountNotInterest;

    @Enumerated(EnumType.STRING)
    private ETypeTransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private ETypeDescription description;

    @Enumerated(EnumType.STRING)
    private ETypeFrecuency paymentFrequency;

    private Double interestRateByPaymentFrequency;

    private Integer installments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_plan_id")
    private List<InstallmentAmount> amountForEachInstallmentId;
}

