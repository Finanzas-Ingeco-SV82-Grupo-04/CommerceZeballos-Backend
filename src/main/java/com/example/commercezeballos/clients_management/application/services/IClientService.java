package com.example.commercezeballos.clients_management.application.services;
import com.example.commercezeballos.clients_management.application.dtos.response.ClientResponseDto;
import java.util.List;

public interface IClientService {
    List<ClientResponseDto> getAllClients();
    boolean deleteByDni(String dni);
}
