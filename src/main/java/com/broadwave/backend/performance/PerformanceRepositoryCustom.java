package com.broadwave.backend.performance;

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

    List<Performance> findByPiAutoNumAndInsert_idDel(String autoNum, String insert_id);

    Performance findByBusiness(String autoNum, String insert_id);

    PerformancePiBusinessDto findByInsertIAndAutoNumAndCount(String insert_id, String autoNum, int count);
}
