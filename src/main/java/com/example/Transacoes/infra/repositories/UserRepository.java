package com.example.Transacoes.infra.repositories;

import com.example.Transacoes.domain.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserApp, UUID> {
    Boolean existsByAccountNumber(String accountNumber);
}
