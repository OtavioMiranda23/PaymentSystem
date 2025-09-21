package com.example.Transacoes.domain.application.usecases;

import com.example.Transacoes.domain.entities.Tax;
import com.example.Transacoes.infra.repositories.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CalculateTax {
    @Autowired
    private TaxRepository taxRepository;

    public BigDecimal execute(BigDecimal amount ,LocalDateTime toFulfillAt) throws Exception {
        //buscar a tabela no banco
        Duration intervalDuration = Duration.between(LocalDateTime.now(), toFulfillAt);
        long daysInterval = intervalDuration.toDays();
        Optional<Tax> _tax = this.taxRepository.findTaxByDays(daysInterval);
        if (_tax.isEmpty()) {
            throw new Exception("Transferência não permitida, pois não foi encontrada data aplicável");
        }
        var tax = _tax.get();
        BigDecimal finalAmount = this.calculateFinalAmount(amount, tax.getMoneyTax(), tax.getPercentageTax());
        return finalAmount;
    }

    private BigDecimal calculateFinalAmount(
            BigDecimal amount,
            BigDecimal moneyTax,
            double percentageTax
            ) {
        BigDecimal sumAcrec = amount.add(moneyTax);
        var percentageTaxFormated = percentageTax / 100;
        var taxCalculated = sumAcrec.multiply(BigDecimal.valueOf(percentageTaxFormated));
        var finalAmount = sumAcrec.add(taxCalculated);
        return finalAmount;
    }
}
