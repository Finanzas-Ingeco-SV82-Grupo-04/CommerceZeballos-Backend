package com.example.commercezeballos.clients_management.application.controllers;

import com.example.commercezeballos.clients_management.application.dtos.response.ClientResponseDto;
import com.example.commercezeballos.clients_management.application.services.IClientService;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/find/{dni}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientByDni(@PathVariable String dni) {
        return ResponseEntity.ok(clientService.getClientByDni(dni));
    }

    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<?> deleteByDni(@PathVariable String dni) {

        boolean isDeleted = clientService.deleteByDni(dni);
        String message = isDeleted ? "Client deleted successfully" : "Client not found or could not be deleted";
        ApiResponse<?> response = new ApiResponse<>(isDeleted, message, null);
        return ResponseEntity.ok(response);
    }
}
