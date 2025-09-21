package com.example.Transacoes.domain.application.usecases;

import com.example.Transacoes.infra.controllers.TransactionDto;
import com.example.Transacoes.domain.entities.Transaction;
import com.example.Transacoes.infra.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateTransaction {
    @Autowired
    public TransactionRepository transactionRepository;

    public Transaction execute(
            TransactionDto transactionDto,
            BigDecimal calculatedAmount
    ) {
        Transaction transactionRaw = new Transaction(
                transactionDto.sender(),
                transactionDto.recipient(),
                transactionDto.amount(),
                transactionDto.toFulfillAt(),
                calculatedAmount
        );
        Transaction transaction = this.transactionRepository.save(transactionRaw);
        return transaction;
    }
}
