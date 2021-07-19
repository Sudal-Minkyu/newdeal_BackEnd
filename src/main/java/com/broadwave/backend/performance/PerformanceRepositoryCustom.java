package com.broadwave.backend.performance;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Remark :
 */
public interface PerformanceRepositoryCustom {
    List<PerformanceDto> findByAutoNum(String autoNum);

    PerformanceCheckDto findByInsertId(String insert_id);

    PerformanceMiddleDataDto findByInsertIAndAutoNum(String insert_id, String autoNum);
}
