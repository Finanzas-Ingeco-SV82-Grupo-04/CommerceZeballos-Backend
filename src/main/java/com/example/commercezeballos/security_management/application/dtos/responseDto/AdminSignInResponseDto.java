package com.example.commercezeballos.security_management.application.dtos.responseDto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminSignInResponseDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String phone;
    private String dtype;


}
