package com.example.commercezeballos.security_management.application.dtos.responseDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSignInResponseDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String dni;
    private String phone;
    private String dtype;//TODO: remove this field
}
