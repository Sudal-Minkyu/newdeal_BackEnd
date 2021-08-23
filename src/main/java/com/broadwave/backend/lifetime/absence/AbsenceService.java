package com.broadwave.backend.lifetime.absence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Time :
 * Remark : NewDeal Absence Service
*/
@Slf4j
@Service
public class AbsenceService {

    @Autowired
    AbsenceRepositoryCustom absenceRepositoryCustom;

    public AbsenceDto findByLtAbsenceCode(String ltAbsenceCode) {
        return absenceRepositoryCustom.findByLtAbsenceCode(ltAbsenceCode);
    }

}
