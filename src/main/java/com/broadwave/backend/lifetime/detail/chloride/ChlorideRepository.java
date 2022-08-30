package com.broadwave.backend.lifetime.detail.chloride;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2022-08-04
 * Remark :
 */
@Repository
public interface ChlorideRepository extends JpaRepository<Chloride,Long>, ChlorideRepositoryCustom {
    @Query("select a from Chloride a where a.ltDetailAutoNum = :ltDetailAutoNum")
    Optional<Chloride> findByLtDetailAutoNum(String ltDetailAutoNum);
}