package com.broadwave.backend.lifetime.absence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Remark :
 */
@Repository
public class AbsenceRepositoryCustomImpl extends QuerydslRepositorySupport implements AbsenceRepositoryCustom {

    public AbsenceRepositoryCustomImpl() {
        super(Absence.class);
    }

    @Override
    public AbsenceDto findByLtAbsenceCode(String ltAbsenceCode) {
        QAbsence absence = QAbsence.absence;

        JPQLQuery<AbsenceDto> query = from(absence)
                .select(Projections.constructor(AbsenceDto.class,
                        absence.ltAbsenceName,
                        absence.ltDeterioration,
                        absence.ltStandardDeviation,
                        absence.ltRemunerationThree,
                        absence.ltRemunerationTwo,
                        absence.ltRemunerationOne,
                        absence.ltRemunerationNum,
                        absence.ltStatusTwo,
                        absence.ltStatusOne,
                        absence.ltStatusNum
                ));

        query.where(absence.ltAbsenceCode.eq(ltAbsenceCode));

        return query.fetchOne();
    }

}
