package com.example.Transacoes.domain.application.usecases;

import com.example.Transacoes.domain.entities.UserApp;
import com.example.Transacoes.infra.controllers.TransactionDto;
import com.example.Transacoes.domain.entities.Transaction;
import com.example.Transacoes.infra.repositories.TransactionRepository;
import com.example.Transacoes.infra.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CreateTransaction {
    @Autowired
    public TransactionRepository transactionRepository;

    @Autowired
    public UserRepository userRepository;

    public Transaction execute(
            TransactionDto transactionDto,
            BigDecimal calculatedAmount
    ) {
        Optional<UserApp> _sender = this.userRepository.findById(transactionDto.senderId());
        UserApp sender = _sender.orElseThrow();
        Optional<UserApp> _recipient = this.userRepository.findById(transactionDto.recipientId());
        UserApp recipient = _recipient.orElseThrow();
        Transaction transactionRaw = new Transaction(
                sender,
                recipient,
                transactionDto.amount(),
                transactionDto.toFulfillAt(),
                calculatedAmount
        );
        Transaction transaction = this.transactionRepository.save(transactionRaw);
        return transaction;
    }
}
