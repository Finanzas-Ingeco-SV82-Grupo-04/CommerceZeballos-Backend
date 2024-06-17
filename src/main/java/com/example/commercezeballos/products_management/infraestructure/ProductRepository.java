package com.example.commercezeballos.products_management.infraestructure;

import com.example.commercezeballos.products_management.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Traer todos los productos menos los que no estan activos
    Page<Product> findByActiveTrue(Pageable pageable);

    //Buscar productos por nombre y que esten activos
    Page<Product> findByNameContainingAndActiveTrue(String name, Pageable pageable);
}
