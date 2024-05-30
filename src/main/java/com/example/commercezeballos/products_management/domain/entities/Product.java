package com.example.commercezeballos.products_management.domain.entities;


import com.example.commercezeballos.current_account_management.domain.entities.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "products",fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();


}
