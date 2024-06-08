package com.example.commercezeballos.security_management.application.dtos.requestDto;


import com.example.commercezeballos.security_management.domain.enums.ERole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolRequestDto {

    @NotNull(message = "Role name is required")
    private ERole role;
}
