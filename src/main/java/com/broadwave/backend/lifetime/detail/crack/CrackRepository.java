package com.broadwave.backend.lifetime.detail.crack;

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
public interface CrackRepository extends JpaRepository<Crack,Long>, CrackRepositoryCustom {
    @Query("select a from Crack a where a.ltDetailAutoNum = :ltDetailAutoNum")
    Optional<Crack> findByLtDetailAutoNum(String ltDetailAutoNum);
}