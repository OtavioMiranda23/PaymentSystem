package com.example.Transacoes.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@NoArgsConstructor
@Data
public class Transaction {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "sender_id", nullable = false)
   private UserApp sender;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "recipient_id", nullable = false)
   private UserApp recipient;

   @NotNull
   @Column(nullable = false)
   private BigDecimal amount;

   @NotNull
   @Column(nullable = false)
   private LocalDateTime toFulfilledAt;

   @NotNull
   @Column(nullable = false)
   private LocalDateTime createdAt;

   private BigDecimal taxedAmount;


   public Transaction(
           UserApp sender,
           UserApp recipient,
           BigDecimal amount,
           LocalDateTime toFulfilledAt,
           BigDecimal taxedAmount
   ){
      LocalDateTime today = LocalDateTime.now();
      if (sender.getAccountNumber().equals(recipient.getAccountNumber())) {
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
      if (taxedAmount.compareTo(amount) < 0 ) {
         throw new IllegalArgumentException("O valor final taxado precisa ser maior ou igual ao valor do depósito");
      }
      this.taxedAmount = taxedAmount;
      this.createdAt = LocalDateTime.now();
   }
}