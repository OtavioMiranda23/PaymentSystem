package com.example.Transacoes.adapters.controllers;

import com.example.Transacoes.application.usecases.CalculateTax;
import com.example.Transacoes.application.usecases.CreateTransaction;
import com.example.Transacoes.domain.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController()
@RequestMapping("/api/transactions")
public class Transactions {
    @Autowired
    private CalculateTax calculateTax;

    @Autowired
    private CreateTransaction createTransaction;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionDto transactionDto) {
        LocalDateTime fulfilledAt = transactionDto.fulfiledAt;
        String sender = transactionDto.sender;
        String recipient = transactionDto.recipient;
        BigDecimal amount = transactionDto.amount;
        BigDecimal taxedAmount = calculateTax.execute(fulfiledAt);
        Transaction transaction = createTransaction.execute(sender, recipient, amount, fulfilledAt, taxedAmount );
        return ResponseEntity.ok().body(transaction);
    }
}
