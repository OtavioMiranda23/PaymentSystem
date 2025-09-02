package com.example.Transacoes.application.usecases;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CalculateTax {
    public BigDecimal execute(LocalDateTime transactionfulfiledAt) {
        return new BigDecimal("0.0");
    }
}
