package com.broadwave.backend.performance.reference.economy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceEconomyRepository extends JpaRepository<ReferenceEconomy,Long> {
    @Query("SELECT a FROM ReferenceEconomy a WHERE a.id = :id")
    ReferenceEconomy ecoData(String id);
}