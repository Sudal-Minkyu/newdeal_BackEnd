package com.broadwave.backend.lifetime.absence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Autowired
    AbsenceRepository absenceRepository;

    public AbsenceDto findByLtAbsenceCode(String ltAbsenceCode) {
        return absenceRepositoryCustom.findByLtAbsenceCode(ltAbsenceCode);
    }

    public Page<AbsenceListDto> findByAbsenceList(String ltAbsence, String ltAbsenceCode, String ltAbsenceName, Pageable pageable) {
        return absenceRepositoryCustom.findByAbsenceList(ltAbsence,ltAbsenceCode,ltAbsenceName,pageable);
    }

    public Optional<Absence> findByAbsenceCode(String ltAbsenceCode) {
        return absenceRepository.findByAbsenceCode(ltAbsenceCode);
    }

    public Optional<Absence> findById(Long id) {
        return absenceRepository.findById(id);
    }

    public void delete(Absence absence) {
        absenceRepository.delete(absence);
    }

    public void save(Absence absence) {
        absenceRepository.save(absence);
    }
}
