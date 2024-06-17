package com.example.commercezeballos.products_management.application.services;

import com.example.commercezeballos.products_management.application.dtos.request.ProductRequestDto;
import com.example.commercezeballos.products_management.application.dtos.response.ProductResponseDto;
import com.example.commercezeballos.shared.model.dto.pageResponse.PageResponse;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ApiResponse<?> registerProduct(ProductRequestDto productRequestDto , MultipartFile file);

    ApiResponse<PageResponse<ProductResponseDto>> getAllProducts(Pageable pageable);

    ApiResponse<PageResponse<ProductResponseDto>> searchProducts(String name, Pageable pageable);

    ApiResponse<?> getProductById(Long id);

    ApiResponse<?> updateProduct(Long id, ProductRequestDto productRequestDto, MultipartFile file);

    ApiResponse<?> deleteProduct(Long id);
}
