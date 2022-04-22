package com.broadwave.backend.performance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance,Long> {
    // 중간저장 데이터 조회
    @Query("select a from Performance a where a.piAutoNum = :autoNum and a.insert_id = :insert_id group by a.piAutoNum order by a.id asc")
    Optional<Performance> findByPiAutoNumAndInsert_id(String autoNum, String insert_id);

    // 우수대안 업데이트
    @Query("select dbTable from Performance dbTable where dbTable.piAutoNum = :autoNum and dbTable.insert_id = :insert_id and dbTable.piInputCount = :piInputCount")
    Optional<Performance> findByPiAutoNumAndInsert_idAndPiInputCount(String autoNum, String insert_id, Integer piInputCount);
}