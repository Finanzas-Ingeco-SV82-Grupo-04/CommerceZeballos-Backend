package com.example.commercezeballos.current_account_management.application.services.impl;

import com.example.commercezeballos.current_account_management.application.dtos.request.RegisterAccountRequestDto;
import com.example.commercezeballos.current_account_management.application.dtos.request.UpdateCurrentAccountRequestDto;
import com.example.commercezeballos.current_account_management.application.dtos.response.CurrentAccountResponseDto;
import com.example.commercezeballos.current_account_management.application.services.AccountService;
import com.example.commercezeballos.current_account_management.domain.entities.CurrentAccount;
import com.example.commercezeballos.current_account_management.infraestructure.CurrentAccountRepository;
import com.example.commercezeballos.security_management.infraestructure.repositories.ClientRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ApplicationException;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class IAccountService implements AccountService {

    private final CurrentAccountRepository currentAccountRepository;

    private final ClientRepository clientRepository;

    private final ModelMapperConfig modelMapperConfig;

    public IAccountService(CurrentAccountRepository currentAccountRepository, ClientRepository clientRepository, ModelMapperConfig modelMapperConfig) {
        this.currentAccountRepository = currentAccountRepository;
        this.clientRepository = clientRepository;
        this.modelMapperConfig = modelMapperConfig;
    }

    @Transactional
    @Override
    public ApiResponse<CurrentAccountResponseDto> registerCurrentAccount(RegisterAccountRequestDto accountRequestDto) {
        if (!clientRepository.existsByDni(accountRequestDto.getDniClient())) {
            throw new ApplicationException( HttpStatus.BAD_REQUEST ,"Client not found");
        }

        if(currentAccountRepository.existsByDniClientAndActiveIsTrue(accountRequestDto.getDniClient())){
            throw new ApplicationException( HttpStatus.BAD_REQUEST ,"Current Account already exists for this client");
        }

        if(currentAccountRepository.existsByDniClientAndActiveIsFalse(accountRequestDto.getDniClient())){
            //reactivar cuenta
            var currentAccount = currentAccountRepository.findByDniClient(accountRequestDto.getDniClient()).get();
            currentAccount.setActive(true);
            currentAccount.setTypeInterest(accountRequestDto.getTypeInterest());
            currentAccount.setPaymentDate(accountRequestDto.getPaymentDate());
            currentAccount.setAccountClosingDate(accountRequestDto.getAccountClosingDate());
            currentAccount.setInterestRate(accountRequestDto.getInterestRate());
            currentAccount.setMoratoriumRate(accountRequestDto.getMoratoriumRate());
            currentAccount.setCreditLimit(accountRequestDto.getCreditLimit());

            currentAccount.setOpeningDate(LocalDateTime.now());

            //Extraer el dia de la fecha de pago y asignarle a paymentDay
            var paymentDate= currentAccount.getPaymentDate();
            var paymentDay= paymentDate.getDayOfMonth();
            currentAccount.setPaymentDay(paymentDay);

            var currentAccountSaved = currentAccountRepository.save(currentAccount);
            var currentAccountResponseDto = modelMapperConfig.modelMapper().map(currentAccountSaved, CurrentAccountResponseDto.class);
            System.out.println("cuenta reactivada");
            return new ApiResponse<>(true," Current Account update successful register " ,currentAccountResponseDto);

        }

        var currentAccount= modelMapperConfig.modelMapper().map(accountRequestDto, CurrentAccount.class);

        currentAccount.setOpeningDate(LocalDateTime.now());

        //Extraer el dia de la fecha de pago y asignarle a paymentDay
        var paymentDate= currentAccount.getPaymentDate();
        var paymentDay= paymentDate.getDayOfMonth();
        currentAccount.setPaymentDay(paymentDay);
        currentAccount.setUsedCredit(accountRequestDto.getCreditLimit());
        currentAccount.setActive(true);

        var currentAccountSaved = currentAccountRepository.save(currentAccount);

        var currentAccountResponseDto = modelMapperConfig.modelMapper().map(currentAccountSaved, CurrentAccountResponseDto.class);

        //este
        System.out.println("cuenta creada");
        return new ApiResponse<>(true," Current Account sucessfull register " ,currentAccountResponseDto);

    }

    @Transactional
    @Override
    public ApiResponse<CurrentAccountResponseDto> updateCurrentAccount(String dni, UpdateCurrentAccountRequestDto updateCurrentAccountRequestDto) {
        if (!clientRepository.existsByDni(dni)) {
            throw new ResourceNotFoundException("Client not found");
        }

        var currentAccount = currentAccountRepository.findByDniClient(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Current Account not found for this client"));

        currentAccount.setTypeInterest(updateCurrentAccountRequestDto.getTypeInterest());
        currentAccount.setCreditLimit(updateCurrentAccountRequestDto.getCreditLimit());
        currentAccount.setUsedCredit(updateCurrentAccountRequestDto.getCreditLimit());
        currentAccount.setPaymentDate(updateCurrentAccountRequestDto.getPaymentDate());
        currentAccount.setAccountClosingDate(updateCurrentAccountRequestDto.getAccountClosingDate());
        currentAccount.setInterestRate(updateCurrentAccountRequestDto.getInterestRate());
        currentAccount.setMoratoriumRate(updateCurrentAccountRequestDto.getMoratoriumRate());

        var currentAccountSaved = currentAccountRepository.save(currentAccount);

        var currentAccountResponseDto = modelMapperConfig.modelMapper().map(currentAccountSaved, CurrentAccountResponseDto.class);

        return new ApiResponse<>(true," Current Account successfull update " ,currentAccountResponseDto);
    }

    @Override
    public ApiResponse<?> deleteCurrentAccount(String dni) {


        var currentAccount = currentAccountRepository.findByDniClient(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Current Account not found for this client"));

        currentAccountRepository.delete(currentAccount);

        return new ApiResponse<>(true," Current Account successfull delete " ,null);
    }

    @Override
    public ApiResponse<CurrentAccountResponseDto> findCurrentAccountByDni(String dni) {
        if(!currentAccountRepository.existsByDniClient(dni)){
            throw new ResourceNotFoundException(" Current Account not found for this client");
        }

        var currentAccount = currentAccountRepository.findByDniClient(dni);

        var currentAccountResponseDto = modelMapperConfig.modelMapper().map(currentAccount, CurrentAccountResponseDto.class);

        return new ApiResponse<>(true," Current Account successfull find " ,currentAccountResponseDto);
    }
}
