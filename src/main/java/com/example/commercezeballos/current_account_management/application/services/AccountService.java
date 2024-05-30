package com.example.commercezeballos.current_account_management.application.services;

import com.example.commercezeballos.current_account_management.application.dtos.request.RegisterAccountRequestDto;
import com.example.commercezeballos.current_account_management.application.dtos.request.UpdateCurrentAccountRequestDto;
import com.example.commercezeballos.current_account_management.application.dtos.response.CurrentAccountResponseDto;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;

public interface AccountService {

    ApiResponse<CurrentAccountResponseDto> registerCurrentAccount(RegisterAccountRequestDto accountRequestDto);

    ApiResponse<CurrentAccountResponseDto> updateCurrentAccount(String dni, UpdateCurrentAccountRequestDto updateCurrentAccountRequestDto);

    ApiResponse<?> deleteCurrentAccount(String dni);

    ApiResponse<CurrentAccountResponseDto> findCurrentAccountByDni(String dni);


}
