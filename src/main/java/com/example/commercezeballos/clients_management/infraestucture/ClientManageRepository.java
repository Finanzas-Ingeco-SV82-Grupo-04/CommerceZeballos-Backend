package com.example.commercezeballos.clients_management.infraestucture;

import com.example.commercezeballos.security_management.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientManageRepository extends JpaRepository<Client, Long> {
    void deleteByDni(String dni);
    Optional<Client> findByDni(String dni);
}
