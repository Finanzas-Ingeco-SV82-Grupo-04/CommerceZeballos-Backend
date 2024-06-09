package com.example.commercezeballos.security_management.infraestructure.repositories;
import com.example.commercezeballos.clients_management.application.dtos.response.ClientResponseDto;
import com.example.commercezeballos.security_management.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Long> {

    Boolean existsByDni(String dni);
}
