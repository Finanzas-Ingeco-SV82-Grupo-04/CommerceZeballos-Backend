package com.example.commercezeballos.products_management.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Boolean active;
}
