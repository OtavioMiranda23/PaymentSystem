package com.example.Transacoes.domain.application.usecases;

import com.example.Transacoes.domain.entities.Tax;
import com.example.Transacoes.infra.repositories.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CalculateTax {
    @Autowired
    private TaxRepository taxRepository;

    public BigDecimal execute(BigDecimal amount ,LocalDateTime toFulfillAt) throws Exception {
        //buscar a tabela no banco
        this.taxRepository.
        var taxTable = new Tax();
        long minDays = taxTable.getMinValue();
        long maxDays = taxTable.getMaxValue();
        BigDecimal moneyTax = taxTable.getMoneyTax();
        double percentageTax = taxTable.getPercentageTax();
        Duration transferenceDays = Duration.between(LocalDateTime.now(), toFulfillAt);
        if (transferenceDays.toDays() >= minDays && transferenceDays.toDays() <= maxDays) {
            return this.calculateFinalAmount(amount, moneyTax, percentageTax);
        } else {
            throw new Exception("Transferência não permitida, pois não foi encontrada data aplicável");
        }
    }

    private BigDecimal calculateFinalAmount(
            BigDecimal amount,
            BigDecimal moneyTax,
            double percentageTax
            ) {
        BigDecimal sumAcrec = amount.add(moneyTax);
        var taxCalculated = sumAcrec.divide(new BigDecimal(percentageTax));
        var finalAmount = amount.add(taxCalculated);
        return finalAmount;
    }
}
