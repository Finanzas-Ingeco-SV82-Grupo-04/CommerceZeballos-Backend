package com.example.commercezeballos.security_management.infraestructure.repositories;

import com.example.commercezeballos.security_management.domain.entities.Role;
import com.example.commercezeballos.security_management.domain.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Role, Long> {
    //find by name
    Optional<Role> findByRolName(ERole rolName);

    //exists by name
    boolean existsByRolName(ERole rolName);
}
