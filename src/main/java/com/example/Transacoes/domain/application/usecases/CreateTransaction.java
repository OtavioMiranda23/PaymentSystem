package com.example.Transacoes.domain.application.usecases;

import com.example.Transacoes.infra.controllers.TransactionDto;
import com.example.Transacoes.domain.entities.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateTransaction {
    public Transaction execute(
            TransactionDto transactionDto,
            BigDecimal calculatedAmount
    ) {
        BigDecimal tax = calculatedAmount.subtract(transactionDto.amount());
        Transaction transaction = new Transaction(
                transactionDto.sender(),
                transactionDto.recipient(),
                transactionDto.amount(),
                transactionDto.toFulfillAt(),
                tax.floatValue()
        );
        return transaction;
    }
}
