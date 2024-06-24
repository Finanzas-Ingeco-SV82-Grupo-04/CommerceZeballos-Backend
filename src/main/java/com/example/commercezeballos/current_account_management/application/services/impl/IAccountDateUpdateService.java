package com.example.commercezeballos.current_account_management.application.services.impl;

import com.example.commercezeballos.current_account_management.application.dtos.response.CurrentAccountResponseDto;
import com.example.commercezeballos.current_account_management.domain.entities.CurrentAccount;
import com.example.commercezeballos.current_account_management.infraestructure.CurrentAccountRepository;
import com.example.commercezeballos.current_account_management.infraestructure.PaymentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IAccountDateUpdateService {

    private final CurrentAccountRepository currentAccountRepository;

    public IAccountDateUpdateService(CurrentAccountRepository currentAccountRepository) {
        this.currentAccountRepository = currentAccountRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")//Every day at 00:00
    public void updatePaymentDate() {
        LocalDate today = LocalDate.now();
        List<CurrentAccount> currentAccounts = currentAccountRepository.findAll();

        /*
        for (CurrentAccount currentAccount : currentAccounts) {
            LocalDate paymentDate = currentAccount.getPaymentDate();
            if (paymentDate.isBefore(today)) {
                paymentDate = paymentDate.plusMonths(1);//Add a month
                currentAccount.setPaymentDate(paymentDate);
                currentAccountRepository.save(currentAccount);
            }
        }*/
    }
}
