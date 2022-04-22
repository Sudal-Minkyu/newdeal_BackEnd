package com.broadwave.backend.performance.reference.technicality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceTechnicalityRepository extends JpaRepository<ReferenceTechnicality,Long> {
    @Query("SELECT a FROM ReferenceTechnicality a WHERE a.id = :id")
    ReferenceTechnicality techData(String id);
}