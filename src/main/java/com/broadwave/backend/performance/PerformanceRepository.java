package com.broadwave.backend.performance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance,Long> {
    // 중간저장 데이터 삭제
    @Query("select a from Performance a where a.piAutoNum = :autoNum and a.insert_id = :insert_id and a.piInputMiddleSave = 0")
    Optional<Performance> findByPiAutoNumAndInsert_id(String autoNum, String insert_id);
}