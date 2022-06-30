package com.broadwave.backend.lifetime.detail.cabonationThreePlate;

/**
 * @author Minkyu
 * Date : 2022-06-29
 * Remark :
 */
public interface CabonationThreePlateRepositoryCustom {
    CabonationThreePlateInfoDto findByLtAutoNum(String autoNum);
}
