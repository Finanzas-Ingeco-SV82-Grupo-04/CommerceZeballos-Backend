package com.example.commercezeballos.clients_management.application.services;
import com.example.commercezeballos.clients_management.application.dtos.response.ClientResponseDto;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface IClientService {
    List<ClientResponseDto> getAllClients();

    ApiResponse<ClientResponseDto> getClientByDni(String dni);

    boolean deleteByDni(String dni);
}
