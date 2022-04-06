package com.broadwave.backend.safety.calculation;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Remark :
 */
public interface CalculationRepositoryCustom {

    List<CalculationListDto> findByCalculationList(Long id);

}
