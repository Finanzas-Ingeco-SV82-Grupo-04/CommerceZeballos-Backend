package com.example.commercezeballos.products_management.application.services.impl;

import com.example.commercezeballos.products_management.application.dtos.request.ProductRequestDto;
import com.example.commercezeballos.products_management.application.services.ProductService;
import com.example.commercezeballos.products_management.domain.entities.Product;
import com.example.commercezeballos.products_management.infraestructure.ProductRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import com.example.commercezeballos.shared.storage.FirebaseFileService;
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
            var imageNewUrl = firebaseFileService.saveImage(file);

            var product = modelMapperConfig.modelMapper().map(productRequestDto, Product.class);
            product.setImageUrl(imageNewUrl);

            productRepository.save(product);

        } catch (IOException e) {
            e.printStackTrace(); //
            return new ApiResponse<>(false,"Error", null);
        }
        return new ApiResponse<>(true,"Product registered", null);
    }

    @Override
    public ApiResponse<?> getAllProducts() {
        var products = productRepository.findAll();
        return new ApiResponse<>(true,"Products list", products);
    }

    @Override
    public ApiResponse<?> getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return new ApiResponse<>(true,"Product", product);
    }

    @Override
    public ApiResponse<?> updateProduct(Long id, ProductRequestDto productRequestDto) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        modelMapperConfig.modelMapper().map(productRequestDto, product);
        productRepository.save(product);

        return new ApiResponse<>(true,"Product updated", null);
    }

    @Override
    public ApiResponse<?> deleteProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);

        return new ApiResponse<>(true,"Product deleted", null);
    }
}
