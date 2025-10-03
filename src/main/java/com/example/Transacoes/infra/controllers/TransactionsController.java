package com.example.Transacoes.infra.controllers;

import com.example.Transacoes.domain.application.usecases.CalculateTax;
import com.example.Transacoes.domain.application.usecases.CreateTransaction;
import com.example.Transacoes.domain.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

@RestController()
@RequestMapping("/api/users/{userId}/transactions")
public class TransactionsController {
    @Autowired
    private CalculateTax calculateTax;

    @Autowired
    private CreateTransaction createTransaction;

//    @GetMapping
//    public  ResponseEntity<?> getAll(@Pa)

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable UUID userId,
            @RequestBody TransactionDto transactionDto
    ) {
        try {
            BigDecimal taxedAmount = calculateTax.execute(transactionDto.amount(), transactionDto.toFulfillAt());
            Transaction transaction = createTransaction.execute(userId, transactionDto, taxedAmount );
            var transactionResponse = new TransactionResponseDto(
                    transaction.getId(),
                    new UserResponseDto(
                            transaction.getSender().getName(),
                            transaction.getSender().getAccountNumber()
                    ),
                    new UserResponseDto(
                            transaction.getRecipient().getName(),
                            transaction.getRecipient().getAccountNumber()
                    ),
                    transaction.getAmount(),
                    transaction.getToFulfilledAt(),
                    transaction.getCreatedAt(),
                    transaction.getTaxedAmount()
            );
            return ResponseEntity.ok().body(transactionResponse);
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
