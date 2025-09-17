package com.example.Transacoes.infra.controllers;

import com.example.Transacoes.domain.application.usecases.CalculateTax;
import com.example.Transacoes.domain.application.usecases.CreateTransaction;
import com.example.Transacoes.domain.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController()
@RequestMapping("/api/transactions")
public class Transactions {
    @Autowired
    private CalculateTax calculateTax;

    @Autowired
    private CreateTransaction createTransaction;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionDto transactionDto) {
        try {
            BigDecimal taxedAmount = calculateTax.execute(transactionDto.amount(), transactionDto.toFulfillAt());
            Transaction transaction = createTransaction.execute(transactionDto, taxedAmount );
            return ResponseEntity.ok().body(transaction);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
