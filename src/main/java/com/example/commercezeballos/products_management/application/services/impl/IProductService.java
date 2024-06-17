package com.example.commercezeballos.products_management.application.services.impl;

import com.example.commercezeballos.products_management.application.dtos.request.ProductRequestDto;
import com.example.commercezeballos.products_management.application.dtos.response.ProductResponseDto;
import com.example.commercezeballos.products_management.application.services.ProductService;
import com.example.commercezeballos.products_management.domain.entities.Product;
import com.example.commercezeballos.products_management.infraestructure.ProductRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.pageResponse.PageResponse;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import com.example.commercezeballos.shared.storage.FirebaseFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class IProductService implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapperConfig modelMapperConfig;

    private final FirebaseFileService firebaseFileService;

    public IProductService(ProductRepository productRepository, ModelMapperConfig modelMapperConfig, FirebaseFileService firebaseFileService) {
        this.productRepository = productRepository;
        this.modelMapperConfig = modelMapperConfig;
        this.firebaseFileService = firebaseFileService;
    }

    @Transactional
    @Override
    public ApiResponse<?> registerProduct(ProductRequestDto productRequestDto, MultipartFile file) {
        try {

            // Guardar la imagen en Firebase Storage
            var imageNewUrl = firebaseFileService.saveImage(file);

            var product = modelMapperConfig.modelMapper().map(productRequestDto, Product.class);
            product.setImageUrl(imageNewUrl);

            product.setActive(true);

            productRepository.save(product);

        } catch (IOException e) {
            e.printStackTrace(); //
            return new ApiResponse<>(false,"Error", null);
        }
        return new ApiResponse<>(true,"Product registered", null);
    }

    @Override
    public ApiResponse<PageResponse<ProductResponseDto>> getAllProducts(Pageable pageable) {
        var products = productRepository.findByActiveTrue(pageable)
                .map(product -> modelMapperConfig.modelMapper().map(product, ProductResponseDto.class));

        PageResponse<ProductResponseDto> pageResponse = PageResponse.<ProductResponseDto>builder()
                .content(products.getContent())
                .currentPage(products.getNumber())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .pageNumber(products.getNumber())
                .build();

        return new ApiResponse<>(true,"Products list", pageResponse);
    }


    @Override
    public ApiResponse<PageResponse<ProductResponseDto>> searchProducts(String name, Pageable pageable) {
        var products = productRepository.findByNameContainingAndActiveTrue(name, pageable)
                .map(product -> modelMapperConfig.modelMapper().map(product, ProductResponseDto.class));

        PageResponse<ProductResponseDto> pageResponse = PageResponse.<ProductResponseDto>builder()
                .content(products.getContent())
                .currentPage(products.getNumber())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .pageNumber(products.getNumber())
                .build();

        return new ApiResponse<>(true,"Products list", pageResponse);
    }

    @Override
    public ApiResponse<?> getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return new ApiResponse<>(true,"Product", product);
    }

    @Override
    public ApiResponse<?> updateProduct(Long id, ProductRequestDto productRequestDto,MultipartFile file) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        try {

            //La imagen en la actualización es opcional
            if(file != null || !file.isEmpty()){
                // Guardar la imagen en Firebase Storage
                var imageNewUrl = firebaseFileService.saveImage(file);
                product.setImageUrl(imageNewUrl);
            }else{
                //Si no se envía una imagen, se mantiene la misma
                product.setImageUrl(product.getImageUrl());
            }

            product.setName(productRequestDto.getName());
            product.setDescription(productRequestDto.getDescription());
            product.setPrice(productRequestDto.getPrice());

            productRepository.save(product);

            


        } catch (IOException e) {
            e.printStackTrace(); //
            return new ApiResponse<>(false,"Error", null);
        }

        return new ApiResponse<>(true,"Product updated", null);



    }

    @Override
    public ApiResponse<?> deleteProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setActive(false);
        productRepository.save(product);

        return new ApiResponse<>(true,"Product desactive/deleted", null);
    }
}
