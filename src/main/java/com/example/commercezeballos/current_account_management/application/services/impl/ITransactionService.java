package com.example.commercezeballos.current_account_management.application.services.impl;

import com.example.commercezeballos.current_account_management.application.dtos.request.TransactionRequestDto;
import com.example.commercezeballos.current_account_management.application.dtos.response.TransactionResponseDto;
import com.example.commercezeballos.current_account_management.application.services.TransactionService;
import com.example.commercezeballos.current_account_management.domain.entities.Transaction;
import com.example.commercezeballos.current_account_management.infraestructure.CurrentAccountRepository;
import com.example.commercezeballos.current_account_management.infraestructure.TransactionRepository;
import com.example.commercezeballos.products_management.domain.entities.Product;
import com.example.commercezeballos.products_management.infraestructure.ProductRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ITransactionService implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ModelMapperConfig modelMapperConfig;

    private final ProductRepository productRepository;

    private final CurrentAccountRepository currentAccountRepository;


    public ITransactionService(TransactionRepository transactionRepository, ModelMapperConfig modelMapperConfig, ProductRepository productRepository, CurrentAccountRepository currentAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.modelMapperConfig = modelMapperConfig;
        this.productRepository = productRepository;
        this.currentAccountRepository = currentAccountRepository;
    }

    // Implement methods
    @Override
    public ApiResponse<?>  registerTransaction(TransactionRequestDto transactionRequestDto) {

        var currentAccount = currentAccountRepository.findByDniClient(transactionRequestDto.getDniClient())
                .orElseThrow(() -> new ResourceNotFoundException("Current Account not found"));


        var transaction = modelMapperConfig.modelMapper().map(transactionRequestDto, Transaction.class);

        transaction.setCurrentAccount(currentAccount);

        List<Product> products= new ArrayList<>();
        for(Long productId : transactionRequestDto.getProductsIds()){
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+productId));
            products.add(product);

        }
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setPaymentCompleted(false);
        transaction.setRemainingInstallments(transaction.getInstallments());
        transaction.setProducts(products);

        var transactionSaved = transactionRepository.save(transaction);

        return new ApiResponse<>(true,"Transaction successful register", null);


    }

    @Transactional
    @Override
    public ApiResponse<?>  getAllTransactionsByCurrentAccountId(Long currentAccountId){

        var currentAccount = currentAccountRepository.findById(currentAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Current Account not found"));

        List<Transaction> transactions = transactionRepository.findAllByCurrentAccountId(currentAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Transactions not found"));

        //mapear a transactionResponseDto y los productos solo el id
        List<TransactionResponseDto> transactionResponseDtos = transactions.stream()
                .map(transaction -> {
                    var transactionResponseDto = modelMapperConfig.modelMapper().map(transaction, TransactionResponseDto.class);
                    transactionResponseDto.setProductIds(transaction.getProducts().stream()
                            .map(Product::getId)
                            .collect(Collectors.toList()));
                    return transactionResponseDto;
                })
                .collect(Collectors.toList());

        return new ApiResponse<>(true,"Transactions found", transactionResponseDtos);


    }


}
