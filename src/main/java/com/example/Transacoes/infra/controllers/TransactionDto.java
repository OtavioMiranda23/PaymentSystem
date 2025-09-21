package com.example.Transacoes.infra.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDto (
    UUID senderId,
    UUID recipientId,
    BigDecimal amount,
    LocalDateTime toFulfillAt
    ) {}
