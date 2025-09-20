package com.example.Transacoes.infra.repositories;

import com.example.Transacoes.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
