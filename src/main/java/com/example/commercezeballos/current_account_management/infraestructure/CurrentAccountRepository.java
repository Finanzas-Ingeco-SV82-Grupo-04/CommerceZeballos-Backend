package com.example.commercezeballos.current_account_management.infraestructure;

import com.example.commercezeballos.current_account_management.domain.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {

    Boolean existsByDniClient(String dniClient);

    //existe por dni y active = true
    Boolean existsByDniClientAndActiveIsTrue(String dniClient);
    Boolean existsByDniClientAndActiveIsFalse(String dniClient);


    Optional<CurrentAccount> findByDniClient(String dniClient);

    //actualizar active a true o false por dni
    @Modifying
    @Transactional
    @Query("UPDATE CurrentAccount c SET c.active = :active WHERE c.dniClient = :dniClient")
    void updateActiveByDniClient(String dniClient, boolean active);




}
