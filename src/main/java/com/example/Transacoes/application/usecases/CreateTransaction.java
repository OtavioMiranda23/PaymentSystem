package com.example.Transacoes.application.usecases;

import com.example.Transacoes.domain.entities.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CreateTransaction {
    public Transaction execute(
            String sender,
            String recipient,
            BigDecimal amount,
            LocalDateTime fulfiledAt,
            BigDecimal calculatedAmount
    ) {
        Transaction transaction = new Transaction(sender, recipient, amount, fulfiledAt);
        return transaction;
    }
}
