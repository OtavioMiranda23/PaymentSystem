package com.example.Transacoes.domain.application.usecases;

import com.example.Transacoes.domain.entities.Transaction;
import com.example.Transacoes.infra.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetTransactions {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> execute(UUID userId) {
        List<Transaction> transactions = transactionRepository.findBySender_Id(userId);
        return transactions;
    }
}
