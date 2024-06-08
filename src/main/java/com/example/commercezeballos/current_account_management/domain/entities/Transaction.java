package com.example.commercezeballos.current_account_management.domain.entities;


import com.example.commercezeballos.current_account_management.domain.enums.ETransactionType;
import com.example.commercezeballos.products_management.domain.entities.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "transaction_amount_not_interest")
    private Double transactionAmountNotInterest;

    @Column(name = "transaction_amount_with_interest")
    private Double transactionAmountWithInterest;


    @Column(name = "transaction_type")
    private ETransactionType transactionType;

    @Column(name = "transaction_description", nullable = true)
    private String transactionDescription;

    @Column(name = "payment_completed")
    private Boolean paymentCompleted;// pago completado

    @Column(name = "installments", nullable = true)
    private Integer installments;// numero de cuotas

    @Column(name = "installment_amount", nullable = true)
    private Double installmentAmount;// monto por cuota

    @Column(name = "remaining_installments", nullable = true)
    private Integer remainingInstallments;// cuotas restantes



    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "current_account_id", nullable = false)
    @JsonIgnore
    private CurrentAccount currentAccount;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "transactions_products",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();


    @ManyToMany(mappedBy = "transactions",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();


}
