package com.broadwave.backend.costprediction;

import com.broadwave.backend.costprediction.costpredicionDtos.CostPredictionListDto;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Remark :
 */
public interface CostPredictionRepositoryCustom {
    List<CostPredictionListDto> findByCostPredicionList(String cpName);
}
