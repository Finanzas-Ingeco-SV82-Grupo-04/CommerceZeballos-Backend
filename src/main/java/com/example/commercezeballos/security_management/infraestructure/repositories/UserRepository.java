package com.example.commercezeballos.security_management.infraestructure.repositories;

import com.example.commercezeballos.security_management.domain.entities.Role;
import com.example.commercezeballos.security_management.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    //Find user by email
    Optional<UserEntity> findByEmail(String email);

    //exits user by email
    Boolean existsByEmail(String email);

    //exits user by dni

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE dni = :dni AND user_type = 'client')", nativeQuery = true)
    Boolean existsByDni(@Param("dni") String dni);


    //Add role to user
    @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void addRoleToUser(@Param("userId") Long userId, @Param("roleId") Long roleId);

    //Verifies if user has role
    @Query(value = "SELECT EXISTS(SELECT 1 FROM adminuser_roles WHERE user_id = :userId AND rol_id = :roleId)", nativeQuery = true)
    Long userHasRole(@Param("userId") Long userId,@Param("roleId") Long roleId);


    //Get roles by user id
    @Query(value = "SELECT r FROM Role r INNER JOIN r.userEntity au WHERE au.id = :UserId")
    Set<Role> getRolesByUserId(@Param("UserId") Long UserId);

}

