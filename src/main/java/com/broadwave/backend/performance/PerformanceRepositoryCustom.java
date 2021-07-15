package com.broadwave.backend.performance;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Remark :
 */
public interface PerformanceRepositoryCustom {
    List<PerformanceDto> findByAutoNum(String autoNum);
}
