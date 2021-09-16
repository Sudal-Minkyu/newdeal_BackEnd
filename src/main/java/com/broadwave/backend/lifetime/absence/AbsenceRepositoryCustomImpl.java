package com.broadwave.backend.lifetime.absence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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
                        absence.ltAbsence,
                        absence.ltAbsenceName,
                        absence.ltAbsenceCode,
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

    @Override
    public Page<AbsenceListDto> findByAbsenceList(String ltAbsence, String ltAbsenceCode, String ltAbsenceName, Pageable pageable) {
        QAbsence absence  = QAbsence.absence;

        JPQLQuery<AbsenceListDto> query = from(absence)
                .select(Projections.constructor(AbsenceListDto.class,
                        absence.id,
                        absence.ltAbsence,
                        absence.ltAbsenceCode,
                        absence.ltAbsenceName,
                        absence.ltDeterioration,
                        absence.ltStandardDeviation
                ));

        if (ltAbsence != null && !ltAbsence.isEmpty()){
            query.where(absence.ltAbsence.eq(ltAbsence));
        }
        if (ltAbsenceCode != null && !ltAbsenceCode.isEmpty()){
            query.where(absence.ltAbsenceName.containsIgnoreCase(ltAbsenceName));
        }
        if (ltAbsenceName != null && !ltAbsenceName.isEmpty()){
            query.where(absence.ltAbsenceName.containsIgnoreCase(ltAbsenceName));
        }

        query.orderBy(absence.id.desc());

        final List<AbsenceListDto> absenceList = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(absenceList, pageable, query.fetchCount());
    }

}
