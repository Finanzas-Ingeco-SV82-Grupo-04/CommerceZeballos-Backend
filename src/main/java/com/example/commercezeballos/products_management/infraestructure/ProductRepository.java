package com.example.commercezeballos.products_management.infraestructure;

import com.example.commercezeballos.products_management.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
