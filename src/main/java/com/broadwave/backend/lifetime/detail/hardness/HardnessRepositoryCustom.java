package com.broadwave.backend.lifetime.detail.hardness;

/**
 * @author Minkyu
 * Date : 2022-07-14
 * Remark :
 */
public interface HardnessRepositoryCustom {
    HardnessInfoDto findByLtAutoNum(String autoNum);
}
