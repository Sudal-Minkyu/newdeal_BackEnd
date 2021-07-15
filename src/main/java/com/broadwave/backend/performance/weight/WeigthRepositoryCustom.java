package com.broadwave.backend.performance.weight;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Remark :
 */
public interface WeigthRepositoryCustom {
    WeightDto findByAutoNum(String autoNum);
}
