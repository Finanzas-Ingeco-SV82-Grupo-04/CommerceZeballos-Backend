package com.example.commercezeballos.current_account_management.application.controllers;


import com.example.commercezeballos.current_account_management.application.dtos.request.RegisterAccountRequestDto;
import com.example.commercezeballos.current_account_management.application.dtos.request.UpdateCurrentAccountRequestDto;
import com.example.commercezeballos.current_account_management.application.services.AccountService;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/current-account")
public class CurrentAccountController {

    private final AccountService accountService;

    public CurrentAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerCurrentAccount(@Valid @RequestBody RegisterAccountRequestDto accountRequestDto) {
        var response = accountService.registerCurrentAccount(accountRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update/{dni}")
    public ResponseEntity<ApiResponse<?>> updateCurrentAccount(@Valid @RequestBody UpdateCurrentAccountRequestDto updateCurrentAccountRequestDto, @PathVariable String dni) {
        var response = accountService.updateCurrentAccount(dni, updateCurrentAccountRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<ApiResponse<?>> deleteCurrentAccount(@PathVariable String dni) {
        var response = accountService.deleteCurrentAccount(dni);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find/{dni}")
    public ResponseEntity<ApiResponse<?>> findCurrentAccountByDni(@PathVariable String dni) {
        var response = accountService.findCurrentAccountByDni(dni);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
