package com.example.commercezeballos.security_management.application.controllers;


import com.example.commercezeballos.security_management.application.dtos.requestDto.AdminSignUpRequestDto;
import com.example.commercezeballos.security_management.application.dtos.requestDto.ClientSignUpRequestDto;
import com.example.commercezeballos.security_management.application.dtos.requestDto.UserSingInRequestDto;
import com.example.commercezeballos.security_management.application.dtos.responseDto.ClientSignInResponseDto;
import com.example.commercezeballos.security_management.application.services.UserService;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin/sign-up")
    public ResponseEntity<ApiResponse<?>> signUpAdminUser(@Valid @RequestBody AdminSignUpRequestDto adminSignUpRequestDto) {
        var response = userService.signUpAdminUser(adminSignUpRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/client/sign-up")
    public ResponseEntity<ApiResponse<?>> signUpClientUser(@Valid @RequestBody ClientSignUpRequestDto clientSignUpRequestDto) {
        var response = userService.signUpClientUser(clientSignUpRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/sign-in")
    public ResponseEntity<ApiResponse<?>> signInAdminUser(@Valid @RequestBody UserSingInRequestDto userSingInRequestDto) {
        var response = userService.signInUser(userSingInRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
