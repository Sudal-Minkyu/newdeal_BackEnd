package com.broadwave.backend.performance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Remark :
 */
public interface PerformanceRepositoryCustom {
    List<PerformanceDto> findByAutoNum(String autoNum);

    PerformanceCheckDto findByInsertId(String insert_id);

    PerformanceMiddleDataDto findByInsertIAndAutoNum(String insert_id, String autoNum);

    List<PerformanceMiddleBusinessDataDto> findByInsertIAndAutoNum2(String insert_id, String autoNum);

    List<Performance> findByPiAutoNumAndInsert_idDel(String autoNum, String insert_id,Integer piInputMiddleSave);

    Performance findByBusiness(String autoNum, String insert_id);

    PerformancePiBusinessDto findByInsertIAndAutoNumAndCount(String insert_id, String autoNum, int count);

    Page<PerformanceListDto> findByPerformanceList(String piFacilityType, String piKind, String piFacilityName, String insert_id, Pageable pageable);

}
