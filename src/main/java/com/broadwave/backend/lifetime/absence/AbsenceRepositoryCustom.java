package com.broadwave.backend.lifetime.absence;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Remark :
 */
public interface AbsenceRepositoryCustom {
    AbsenceDto findByLtAbsenceCode(String ltAbsenceCode);
}
