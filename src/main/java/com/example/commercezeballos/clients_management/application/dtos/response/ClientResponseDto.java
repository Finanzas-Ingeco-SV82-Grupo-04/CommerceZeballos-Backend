package com.example.commercezeballos.clients_management.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String dni;
    private String phone;
    private String dtype;
}
