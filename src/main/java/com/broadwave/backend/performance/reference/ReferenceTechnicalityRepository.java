package com.broadwave.backend.performance.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferenceTechnicalityRepository extends JpaRepository<ReferenceTechnicality,Long> {
    @Query("SELECT a FROM ReferenceTechnicality a WHERE a.id = :id")
    ReferenceTechnicality techData(String id);
}