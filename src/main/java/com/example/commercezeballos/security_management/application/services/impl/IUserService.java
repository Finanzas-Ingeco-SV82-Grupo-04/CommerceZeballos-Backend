package com.example.commercezeballos.security_management.application.services.impl;

import com.example.commercezeballos.security_management.application.dtos.requestDto.AdminSignUpRequestDto;
import com.example.commercezeballos.security_management.application.dtos.requestDto.ClientSignUpRequestDto;
import com.example.commercezeballos.security_management.application.dtos.requestDto.UserSingInRequestDto;
import com.example.commercezeballos.security_management.application.dtos.responseDto.AdminSignInResponseDto;
import com.example.commercezeballos.security_management.application.dtos.responseDto.ClientSignInResponseDto;
import com.example.commercezeballos.security_management.application.services.UserService;
import com.example.commercezeballos.security_management.domain.entities.Admin;
import com.example.commercezeballos.security_management.domain.entities.Client;
import com.example.commercezeballos.security_management.domain.entities.Role;
import com.example.commercezeballos.security_management.domain.entities.UserEntity;
import com.example.commercezeballos.security_management.domain.enums.ERole;
import com.example.commercezeballos.security_management.domain.jwt.utils.JwtUtils;
import com.example.commercezeballos.security_management.infraestructure.repositories.ClientRepository;
import com.example.commercezeballos.security_management.infraestructure.repositories.RolRepository;
import com.example.commercezeballos.security_management.infraestructure.repositories.UserRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ApplicationException;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IUserService implements UserService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ModelMapperConfig modelMapperConfig;
    private final RolRepository rolRepository;


    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public IUserService(UserRepository userRepository, ClientRepository clientRepository, ModelMapperConfig modelMapperConfig, RolRepository rolRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.modelMapperConfig = modelMapperConfig;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public ApiResponse<?> signUpAdminUser(AdminSignUpRequestDto adminSignUpRequestDto){

        if(userRepository.existsByEmail(adminSignUpRequestDto.getEmail())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST,"Email already exists");
        }
        adminSignUpRequestDto.setPassword(passwordEncoder.encode(adminSignUpRequestDto.getPassword()));

        var adminUser = modelMapperConfig.modelMapper().map(adminSignUpRequestDto, Admin.class);

        //verificar si el rol existe
        Set<Role> roles=new HashSet<>();
        for( var rol : adminSignUpRequestDto.getRoles()){

            var role = rolRepository.findByRolName(ERole.valueOf(ERole.class, rol))
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            roles.add(role);

        }

        adminUser.setRoles(roles);

        userRepository.save(adminUser);

        return new ApiResponse<>(true, "Admin user registered successfully", null);
    }

    @Override
    public ApiResponse<?> signUpClientUser(ClientSignUpRequestDto clientSignUpRequestDto){
        if(userRepository.existsByEmail(clientSignUpRequestDto.getEmail())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST,"Email already exists");
        }

        if(clientRepository.existsByDni(clientSignUpRequestDto.getDni())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST,"Dni already exists");
        }

        clientSignUpRequestDto.setPassword(passwordEncoder.encode(clientSignUpRequestDto.getPassword()));


        var clientUser = modelMapperConfig.modelMapper().map(clientSignUpRequestDto, Client.class);

        //verificar si el rol existe
        Set<Role> roles=new HashSet<>();
        var rol = "CLIENT";
        var role = rolRepository.findByRolName(ERole.valueOf(ERole.class, rol))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        roles.add(role);

        clientUser.setRoles(roles);

        userRepository.save(clientUser);

        return new ApiResponse<>(true, "Client user registered successfully", null);
    }

    @Transactional
    @Override
    public ApiResponse<?> signInUser(UserSingInRequestDto userSingInRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userSingInRequestDto.getEmail(), userSingInRequestDto.getPassword())
        );
        String papa="true";

        return new ApiResponse<>(true, "Admin user logged in successfully", papa);

    }
}
