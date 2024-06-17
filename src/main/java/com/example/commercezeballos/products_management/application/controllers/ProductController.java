package com.example.commercezeballos.products_management.application.controllers;

import com.example.commercezeballos.products_management.application.dtos.request.ProductRequestDto;
import com.example.commercezeballos.products_management.application.services.ProductService;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerProduct( @Valid @RequestPart ProductRequestDto productRequestDto ,@RequestParam("file")MultipartFile file) {
        var response = productService.registerProduct(productRequestDto, file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse<?>> getAllProducts(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var response = productService.getAllProducts(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<ApiResponse<?>> searchProducts(@RequestParam String name, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var response = productService.searchProducts(name, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ApiResponse<?>> getProductById(@PathVariable Long id) {
        var response = productService.getProductById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable Long id, @Valid @RequestPart ProductRequestDto productRequestDto,@RequestParam(value = "file", required = false )MultipartFile file) {
        var response = productService.updateProduct(id, productRequestDto,file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable Long id) {
        var response = productService.deleteProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
