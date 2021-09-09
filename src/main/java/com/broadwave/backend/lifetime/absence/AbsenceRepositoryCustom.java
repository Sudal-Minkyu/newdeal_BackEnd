package com.broadwave.backend.lifetime.absence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Remark :
 */
public interface AbsenceRepositoryCustom {
    AbsenceDto findByLtAbsenceCode(String ltAbsenceCode);

    Page<AbsenceListDto> findByAbsenceList(String ltAbsence, String ltAbsenceCode, String ltAbsenceName, Pageable pageable);
}
