package com.broadwave.backend.performance.price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-07-07
 * Remark : 뉴딜 관리자 전용 성능개선사업평가 년도별 환율 PriceRepository
 */
@Repository
public interface PriceRepository extends JpaRepository<Price,Long> {

    @Query("select a from Price a where a.piYear = :piYear")
    Optional<Price> findByPiYear(@Param("piYear") Double piYear);

}