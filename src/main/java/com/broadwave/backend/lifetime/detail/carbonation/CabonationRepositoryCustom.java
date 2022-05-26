package com.broadwave.backend.lifetime.detail.carbonation;

/**
 * @author Minkyu
 * Date : 2022-09-15
 * Remark :
 */
public interface CabonationRepositoryCustom {
    CabonationInfoDto findByLtAutoNum(String autoNum);
}
