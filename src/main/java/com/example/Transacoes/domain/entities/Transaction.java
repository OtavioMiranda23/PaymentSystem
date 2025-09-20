package com.example.Transacoes.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@NoArgsConstructor
@Data
public class Transaction {
   @Id
   @GeneratedValue
    private UUID id;

   @NotNull
   @Column(nullable = false)
   private String sender;

   @NotNull
   @Column(nullable = false)
   private String recipient;

   @NotNull
   @Column(nullable = false)
   private BigDecimal amount;

   @NotNull
   @Column(nullable = false)
   private LocalDateTime toFulfilledAt;

   @NotNull
   @Column(nullable = false)
   private LocalDateTime createdAt;
   private Float tax;

   public Transaction(
           String sender,
           String recipient,
           BigDecimal amount,
           LocalDateTime toFulfilledAt,
           Float tax
   ){
      LocalDateTime today = LocalDateTime.now();
      if (sender.equals(recipient)) {
         throw new IllegalArgumentException("Não é possível transferir para a mesma conta");
      }
      this.sender = sender;
      this.recipient = recipient;
      if (amount.compareTo(new BigDecimal(0)) <= 0) {
         throw new IllegalArgumentException("O valor precisa ser maior que 0");
      }
      this.amount = amount;
      if (toFulfilledAt.isBefore(today)) {
         throw new IllegalArgumentException("A data de transferência precisa ser a partir da data de hoje");
      }
      this.toFulfilledAt = toFulfilledAt;
      if (tax < 0) {
         throw new IllegalArgumentException("A taxa precisa ser maior que 0");
      }
      this.tax = tax;
      this.createdAt = LocalDateTime.now();
   }
}