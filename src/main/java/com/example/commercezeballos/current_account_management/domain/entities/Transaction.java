package com.example.commercezeballos.current_account_management.domain.entities;


import com.example.commercezeballos.products_management.domain.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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


    @Column(name = "transaction_description", nullable = true)
    private String transactionDescription;



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



}
