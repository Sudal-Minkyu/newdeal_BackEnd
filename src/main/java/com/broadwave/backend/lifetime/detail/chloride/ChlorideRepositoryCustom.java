package com.broadwave.backend.lifetime.detail.chloride;

import com.broadwave.backend.lifetime.detail.crack.CrackInfoDto;

/**
 * @author Minkyu
 * Date : 2022-08-04
 * Remark :
 */
public interface ChlorideRepositoryCustom {
    ChlorideInfoDto findByLtAutoNum(String autoNum);
}
