package com.broadwave.backend.performance;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Remark :
 */
@Repository
public class PerformanceRepositoryCustomImpl extends QuerydslRepositorySupport implements PerformanceRepositoryCustom {

    public PerformanceRepositoryCustomImpl() {
        super(Performance.class);
    }

    @Override
    public List<PerformanceDto> findByAutoNum(String autoNum){

        QPerformance performance = QPerformance.performance;

        JPQLQuery<PerformanceDto> query = from(performance)
                .select(Projections.constructor(PerformanceDto.class,
                        performance.piFacilityType,
                        performance.piFacilityName,
                        performance.piKind,
                        performance.piCompletionYear,
                        performance.piPublicYear,
                        performance.piType,
                        performance.piErectionCost,
                        performance.piSafetyLevel,
                        performance.piGoalLevel,
                        performance.piMaintenanceDelay,
                        performance.piManagement,
                        performance.piAgency,
                        performance.piAADT,
                        performance.piBusiness,
                        performance.piBusinessType,
                        performance.piTargetAbsence,
                        performance.piBusinessClassification,
                        performance.piBusinessInformation,
                        performance.piBusinessExpenses,
                        performance.piBeforeSafetyRating,
                        performance.piAfterSafetyRating,
                        performance.piBusinessObligatory,
                        performance.piBusinessMandatory,
                        performance.piBusinessPlanned,
                        performance.piWhether,
                        performance.piRaterBaseYear,
                        performance.piRater,
                        performance.piRaterBelong,
                        performance.piRaterPhone,
                        performance.piInputCount
                ));

        // 검색조건필터
        if (autoNum != null && !autoNum.isEmpty()){
            query.where(performance.piAutoNum.eq(autoNum));
        }

        query.orderBy(performance.piInputCount.asc());

        return query.fetch();
    }

    @Override
    public PerformanceCheckDto findByInsertId(String insert_id){

        QPerformance performance = QPerformance.performance;

        JPQLQuery<PerformanceCheckDto> query = from(performance)
                .select(Projections.constructor(PerformanceCheckDto.class,
                        performance.piAutoNum,
                        performance.piInputMiddleSave
                ));

        // 검색조건필터
        if (insert_id != null && !insert_id.isEmpty()){
            query.where(performance.insert_id.eq(insert_id));
        }
        query.where(performance.piInputMiddleSave.eq(0));
        return query.fetchOne();
    }

    @Override
    public  PerformanceMiddleDataDto findByInsertIAndAutoNum(String insert_id,String autoNum){

        QPerformance performance = QPerformance.performance;

        JPQLQuery<PerformanceMiddleDataDto> query = from(performance)
                .select(Projections.constructor(PerformanceMiddleDataDto.class,
                        performance.piFacilityType,
                        performance.piFacilityName,
                        performance.piKind,
                        performance.piCompletionYear,
                        performance.piPublicYear,
                        performance.piType,
                        performance.piErectionCost,
                        performance.piSafetyLevel,
                        performance.piGoalLevel,
                        performance.piMaintenanceDelay,
                        performance.piManagement,
                        performance.piAgency,
                        performance.piAADT
                ));

        // 검색조건필터
        if (insert_id != null && !insert_id.isEmpty()){
            query.where(performance.insert_id.eq(insert_id));
        }
        query.where(performance.piInputMiddleSave.eq(0));
        query.where(performance.piAutoNum.eq(autoNum));

        return query.fetchOne();
    }

}
