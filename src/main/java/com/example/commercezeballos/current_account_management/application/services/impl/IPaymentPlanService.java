package com.example.commercezeballos.current_account_management.application.services.impl;
import com.example.commercezeballos.current_account_management.application.dtos.request.PaymentPlanRequestDto;
import com.example.commercezeballos.current_account_management.application.services.PaymentPlanService;
import com.example.commercezeballos.current_account_management.domain.entities.CurrentAccount;
import com.example.commercezeballos.current_account_management.domain.entities.PaymentPlan;
import com.example.commercezeballos.current_account_management.domain.enums.ETypeFrecuency;
import com.example.commercezeballos.current_account_management.infraestructure.CurrentAccountRepository;
import com.example.commercezeballos.current_account_management.infraestructure.PaymentPlanRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;


@Service
public class IPaymentPlanService implements PaymentPlanService {
    private final PaymentPlanRepository paymentPlanRepository;
    private final CurrentAccountRepository currentAccountRepository;
    private final ModelMapperConfig modelMapperConfig;

    public IPaymentPlanService(
            PaymentPlanRepository paymentPlanRepository,
            CurrentAccountRepository currentAccountRepository,
            ModelMapperConfig modelMapperConfig
            ) {
        this.paymentPlanRepository = paymentPlanRepository;
        this.currentAccountRepository = currentAccountRepository;
        this.modelMapperConfig = modelMapperConfig;
    }
/*
    @Override
    public ApiResponse<?> registerPaymentPlan(PaymentPlanRequestDto paymentPlanRequestDto) {

        var currentAccount = currentAccountRepository.findByDniClient(paymentPlanRequestDto.getDniClient())
                .orElseThrow(() -> new ResourceNotFoundException("Current Account not found"));

        var paymentPlan = modelMapperConfig.modelMapper().map(paymentPlanRequestDto, PaymentPlan.class);
        paymentPlan.setCurrentAccount(currentAccount);

        paymentPlanRepository.save(paymentPlan);

        return new ApiResponse<>(true, "Payment plan registered successfully", null);
    }
*/
    @Override
    public ApiResponse<?> findPaymentPlanByDni(String dni) {
        var paymentPlans = paymentPlanRepository.findByCurrentAccount_DniClient(dni);

        return new ApiResponse<>(true, "Payment plans found successfully", paymentPlans);
    }

    @Override
    public void registerPaymentPlanByNumberPayments(Integer countInstallment, String dniClient) {

        var currentAccount = currentAccountRepository.findByDniClient(dniClient)
                .orElseThrow(() -> new ResourceNotFoundException("Current Account not found"));

        //saldo inicial(credito
        double remainingCapital = currentAccount.getUsedCredit();//credito usado

        //Calcular la tasa de interes segun frecuencia de pago
        double daysInMonth = 30;
        double daysInFrequency = currentAccount.getPaymentFrequency().equals(ETypeFrecuency.SEMANAL)? 7 : 15;
        //TEQ o TESE
        double TEP = (Math.pow(1 + (currentAccount.getInterestRate()/ 100), daysInFrequency / daysInMonth) - 1) ;
        double frequency= currentAccount.getNumberPayments();

        double amountInteresThisPaymentPlan= remainingCapital * TEP;

        double amortizacion= 0;




        for (int i = 0; i < countInstallment; i++) {
            var paymentPlan = new PaymentPlan();
            paymentPlan.setCurrentAccount(currentAccount);
            paymentPlan.setCreditUsedOfCurrentAccount(remainingCapital);
            paymentPlan.setCreditType(currentAccount.getTypeCredit());
            paymentPlan.setPaymentFrequency(currentAccount.getPaymentFrequency());
            paymentPlan.setInterestRateByPaymentFrequency(TEP);
            paymentPlan.setIsPaid(false);

            // Calcular la fecha de inicio del plan de pago
            LocalDate paymentDate = LocalDate.from(currentAccount.getOpeningDate().plusDays(Math.round(daysInFrequency) * (i + 1)));
            paymentPlan.setStartDate(paymentDate);


            switch (currentAccount.getTypeCredit()){
                case AMERICANO -> {
                    paymentPlan.setAmountPerPaymentPlan(remainingCapital * TEP);
                    paymentPlanRepository.save(paymentPlan);
                }
                case FRANCES -> {
                    //cuota inicial
                    double cuota=
                            remainingCapital  *  ((TEP *(Math.pow( (1+TEP),(frequency-(i+1)+1))))/ ((Math.pow(1+TEP,frequency-(i+1)+1))-1));

                    amountInteresThisPaymentPlan= remainingCapital * TEP;
                    amortizacion= cuota - amountInteresThisPaymentPlan;

                    remainingCapital= remainingCapital - amortizacion;
                    paymentPlan.setAmountPerPaymentPlan(cuota);
                    paymentPlanRepository.save(paymentPlan);

                }
            }
        }
    }


    @Transactional
    @Override
    public void updatePaymentPlans(CurrentAccount currentAccount) {
        // Recuperar y ordenar los planes de pago existentes
        List<PaymentPlan> existingPaymentPlans = paymentPlanRepository.findAllByCurrentAccount_DniClient(currentAccount.getDniClient());
        existingPaymentPlans.sort(Comparator.comparing(PaymentPlan::getId));

        // saldo inicial (crédito usado)
        double remainingCapital = currentAccount.getUsedCredit();

        // Calcular la tasa de interés según frecuencia de pago
        double daysInMonth = 30;
        double daysInFrequency = currentAccount.getPaymentFrequency().equals(ETypeFrecuency.SEMANAL) ? 7 : 15;
        double TEP = (Math.pow(1 + (currentAccount.getInterestRate() / 100), daysInFrequency / daysInMonth) - 1);
        double frequency = currentAccount.getNumberPayments();

        double amountInterestThisPaymentPlan = remainingCapital * TEP;
        double amortization = 0;

        for (int i = 0; i < existingPaymentPlans.size(); i++) {
            var paymentPlan = existingPaymentPlans.get(i);
            paymentPlan.setCreditUsedOfCurrentAccount(remainingCapital);
            paymentPlan.setCreditType(currentAccount.getTypeCredit());
            paymentPlan.setPaymentFrequency(currentAccount.getPaymentFrequency());
            paymentPlan.setInterestRateByPaymentFrequency(TEP);


            switch (currentAccount.getTypeCredit()) {
                case AMERICANO -> {
                    paymentPlan.setAmountPerPaymentPlan(remainingCapital * TEP);
                }
                case FRANCES -> {
                    double cuota = remainingCapital * ((TEP * (Math.pow((1 + TEP), (frequency - (i + 1) + 1)))) / ((Math.pow(1 + TEP, frequency - (i + 1) + 1)) - 1));

                    amountInterestThisPaymentPlan = remainingCapital * TEP;
                    amortization = cuota - amountInterestThisPaymentPlan;

                    remainingCapital = remainingCapital - amortization;
                    paymentPlan.setAmountPerPaymentPlan(cuota);
                }
            }
            paymentPlanRepository.save(paymentPlan);
        }
    }



    @Transactional
    @Override
    public void updatePaymentPlanIsPaidById(Long id, Boolean isPaid) {
        paymentPlanRepository.updatePaymentPlanIsPaidById(id, isPaid);
    }
}
