package com.example.Transacoes.infra.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto (
    String sender,
    String recipient,
    BigDecimal amount,
    LocalDateTime toFulfillAt
    ) {}
