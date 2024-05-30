package com.example.commercezeballos.security_management.application.services;

import com.example.commercezeballos.security_management.application.dtos.requestDto.AdminSignUpRequestDto;
import com.example.commercezeballos.security_management.application.dtos.requestDto.ClientSignUpRequestDto;
import com.example.commercezeballos.security_management.application.dtos.requestDto.UserSingInRequestDto;
import com.example.commercezeballos.security_management.application.dtos.responseDto.AdminSignInResponseDto;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;

public interface UserService {

    ApiResponse<?> signUpAdminUser(AdminSignUpRequestDto adminSignUpRequestDto);

    ApiResponse<?> signUpClientUser(ClientSignUpRequestDto clientSignUpRequestDto);


    ApiResponse<?> signInUser(UserSingInRequestDto userSingInRequestDto);
}
