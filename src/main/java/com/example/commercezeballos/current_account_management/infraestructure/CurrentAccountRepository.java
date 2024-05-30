package com.example.commercezeballos.current_account_management.infraestructure;

import com.example.commercezeballos.current_account_management.domain.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {

    Boolean existsByDniClient(String dniClient);

    CurrentAccount findByDniClient(String dniClient);


}
