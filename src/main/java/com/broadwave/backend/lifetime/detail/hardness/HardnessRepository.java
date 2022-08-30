package com.broadwave.backend.lifetime.detail.hardness;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HardnessRepository extends JpaRepository<Hardness,Long>, HardnessRepositoryCustom {
    @Query("select a from Hardness a where a.ltDetailAutoNum = :ltDetailAutoNum")
    Optional<Hardness> findByLtDetailAutoNum(String ltDetailAutoNum);
}