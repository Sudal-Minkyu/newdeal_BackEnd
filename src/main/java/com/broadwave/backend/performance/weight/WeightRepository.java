package com.broadwave.backend.performance.weight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight,Long> {
    @Query("select a from Weight a where a.piAutoNum = :autoNum and a.insert_id = :insert_id")
    Optional<Weight> findByAutoNumAndInsertId(String autoNum, String insert_id);
}