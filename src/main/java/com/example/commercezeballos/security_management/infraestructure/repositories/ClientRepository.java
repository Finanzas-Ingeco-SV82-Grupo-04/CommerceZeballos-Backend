package com.example.commercezeballos.security_management.infraestructure.repositories;

import com.example.commercezeballos.security_management.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Boolean existsByDni(String dni);


}
