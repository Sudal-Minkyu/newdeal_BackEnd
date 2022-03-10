package com.broadwave.backend.performance.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferencePolicyRepository extends JpaRepository<ReferencePolicy,Long> {
    @Query("SELECT a FROM ReferencePolicy a WHERE a.id = :id")
    ReferencePolicy policyData(String id);
}