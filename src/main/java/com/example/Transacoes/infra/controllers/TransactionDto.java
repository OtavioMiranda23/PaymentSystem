package com.example.Transacoes.infra.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDto (
    String recipientAccountNumber,
    BigDecimal amount,
    LocalDateTime toFulfillAt
    ) {}
