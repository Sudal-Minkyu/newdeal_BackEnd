package com.broadwave.backend.performance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance,Long> {

//    void findByAutoNum(String autoNum);
}