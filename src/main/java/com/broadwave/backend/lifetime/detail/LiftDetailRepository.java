package com.broadwave.backend.lifetime.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiftDetailRepository extends JpaRepository<LifeDetail,Long> {
    @Query("select a from LifeDetail a where a.ltDetailAutoNum = :ltDetailAutoNum")
    Optional<LifeDetail> findByLtDetailAutoNum(String ltDetailAutoNum);
}