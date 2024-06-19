package com.example.commercezeballos.current_account_management.domain.entities;

import com.example.commercezeballos.current_account_management.domain.enums.EInterest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "current_accounts")
public class CurrentAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_interest")
    private EInterest typeInterest;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name="used_credit")
    private Double usedCredit;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "opening_date")
    private LocalDateTime openingDate;

    @Column(name = "payment_day")
    private Integer paymentDay;

    @Column(name = "account_closing_date")
    private LocalDate accountClosingDate;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "moratorium_rate")
    private Double moratoriumRate;


    @Column(name = "dni_client")
    private String dniClient;


    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "currentAccount",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();


}
