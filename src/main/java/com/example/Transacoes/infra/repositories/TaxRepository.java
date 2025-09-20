package com.example.Transacoes.infra.repositories;

import com.example.Transacoes.domain.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository <Tax, Long> {
    @Query("SELECT table FROM Tax table WHERE :days between table.minValue AND table.maxValue")
    Optional<Tax> findTaxByDays(@Param("days") long days);
}
