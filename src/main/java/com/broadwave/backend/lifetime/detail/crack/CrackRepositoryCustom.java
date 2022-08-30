package com.broadwave.backend.lifetime.detail.crack;

/**
 * @author Minkyu
 * Date : 2022-08-04
 * Remark :
 */
public interface CrackRepositoryCustom {
    CrackInfoDto findByLtAutoNum(String autoNum);
}
