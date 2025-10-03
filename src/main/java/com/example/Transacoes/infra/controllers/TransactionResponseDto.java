package com.example.Transacoes.infra.controllers;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDto(
        UUID id,
        UserResponseDto sender,
        UserResponseDto recipient,
        BigDecimal amount,
        LocalDateTime toFulfilledAt,
        LocalDateTime createdAt,
        BigDecimal taxedAmount
) {
}
