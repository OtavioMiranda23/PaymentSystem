package com.example.Transacoes.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table
public class UserApp {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String name;

    @NotNull
    @Column(nullable = false, length = 10, unique = true)
    private String accountNumber;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> sentTransactions;


    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> receivedTransactions;

    public UserApp(String name, String accountNumber) {
        this.name = name;
        if (accountNumber.length() != 10) {
            throw new IllegalArgumentException("O número da conta deve ter 10 caracteres");
        }
        this.accountNumber = accountNumber;
    }
}
