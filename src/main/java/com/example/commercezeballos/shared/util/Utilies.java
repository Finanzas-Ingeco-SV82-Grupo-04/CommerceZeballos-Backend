package com.example.commercezeballos.shared.util;

import com.example.commercezeballos.security_management.domain.entities.Role;
import com.example.commercezeballos.security_management.domain.enums.ERole;
import com.example.commercezeballos.security_management.infraestructure.repositories.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class Utilies implements CommandLineRunner {

    private final RolRepository rolRepository;


    public Utilies(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles(){
        Set<ERole> roleNames = new HashSet<>(Arrays.asList(ERole.ADMIN,
                ERole.CLIENT));

        for(ERole roleName : roleNames){
            if (!rolRepository.existsByRolName(roleName)) {
                var newRole = new Role();
                newRole.setRolName(roleName);

                rolRepository.save(newRole);
            }
        }

    }
}