package com.broadwave.backend.performance;

import com.broadwave.backend.performance.performanceDtos.*;
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
                        performance.piUsabilityAndGoalLevel,
                        performance.piMaintenanceDelay,
                        performance.piManagement,
                        performance.piAgency,
                        performance.piAADT,
                        performance.piBusiness,
                        performance.piBusinessType,
                        performance.piTargetAbsence,
                        performance.piBusinessClassification,
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
                        performance.piBusiness,
                        performance.piInputMiddleSave
                ));

        // 검색조건필터
        query.where(performance.insert_id.eq(insert_id));
        query.where(performance.piInputCount.eq(1));
        query.where(performance.piInputMiddleSave.eq(0));

        return query.fetchOne();
    }

    @Override
    public PerformanceMiddleDataDto findByInsertIAndAutoNum(String insert_id, String autoNum){

        QPerformance performance = QPerformance.performance;

        JPQLQuery<PerformanceMiddleDataDto> query = from(performance)
                .select(Projections.constructor(PerformanceMiddleDataDto.class,
                        performance.piBusiness,
                        performance.piFacilityType,
                        performance.piFacilityName,
                        performance.piKind,
                        performance.piCompletionYear,
                        performance.piPublicYear,
                        performance.piType,
                        performance.piErectionCost,
                        performance.piSafetyLevel,
                        performance.piUsabilityAndGoalLevel,
                        performance.piMaintenanceDelay,
                        performance.piManagement,
                        performance.piAgency,
                        performance.piAADT,
                        performance.piRaterBaseYear,
                        performance.piRater,
                        performance.piRaterBelong,
                        performance.piRaterPhone
                ));

        // 검색조건필터
        query.where(performance.insert_id.eq(insert_id));
        query.where(performance.piInputMiddleSave.eq(0));
        query.where(performance.piAutoNum.eq(autoNum));
        query.where(performance.piInputCount.eq(1));
        return query.fetchOne();
    }

    @Override
    public List<PerformanceMiddleBusinessDataDto> findByInsertIAndAutoNum2(String insert_id, String autoNum){

        QPerformance performance = QPerformance.performance;

        JPQLQuery<PerformanceMiddleBusinessDataDto> query = from(performance)
                .select(Projections.constructor(PerformanceMiddleBusinessDataDto.class,
                        performance.id,
                        performance.piBusinessType,
                        performance.piTargetAbsence,
                        performance.piBusinessClassification,
                        performance.piBusinessExpenses,
                        performance.piBeforeSafetyRating,
                        performance.piAfterSafetyRating,
                        performance.piBusinessObligatory,
                        performance.piBusinessMandatory,
                        performance.piBusinessPlanned,
                        performance.piWhether
                ));

        // 검색조건필터
        query.where(performance.insert_id.eq(insert_id));
        query.where(performance.piInputMiddleSave.eq(0));
        query.where(performance.piAutoNum.eq(autoNum));

        return query.fetch();

    }

    // 대안 원본 리스트 가져오기
    @Override
    public List<Performance> findByPiAutoNumAndInsert_idDel(String autoNum, String insert_id, Integer piInputMiddleSave){

        QPerformance performance = QPerformance.performance;

        JPQLQuery<Performance> query = from(performance)
                .select(Projections.constructor(Performance.class,
                        performance.id,

                        performance.piAutoNum,
                        performance.piFacilityType,
                        performance.piFacilityName,
                        performance.piKind,
                        performance.piCompletionYear,
                        performance.piPublicYear,
                        performance.piType,
                        performance.piErectionCost,

                        performance.piSafetyLevel,
                        performance.piUsabilityAndGoalLevel,

                        performance.piMaintenanceDelay,
                        performance.piManagement,
                        performance.piAgency,
                        performance.piAADT,

                        performance.piBusiness,
                        performance.piBusinessType,
                        performance.piTargetAbsence,
                        performance.piBusinessClassification,
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

                        performance.piInputCount,
                        performance.piInputGreat,
                        performance.piInputMiddleSave,

                        performance.insertDateTime,
                        performance.insert_id,
                        performance.modifyDateTime,
                        performance.modify_id
                    ));

        // 검색조건필터
        query.where(performance.insert_id.eq(insert_id));
        query.where(performance.piInputMiddleSave.eq(piInputMiddleSave));
        query.where(performance.piAutoNum.eq(autoNum));

        return query.fetch();
    }

    @Override
    public Performance findByBusiness(String autoNum, String insert_id){

        QPerformance performance = QPerformance.performance;

        JPQLQuery<Performance> query = from(performance)
                .select(Projections.constructor(Performance.class,
                        performance.id,

                        performance.piAutoNum,
                        performance.piFacilityType,
                        performance.piFacilityName,
                        performance.piKind,
                        performance.piCompletionYear,
                        performance.piPublicYear,
                        performance.piType,
                        performance.piErectionCost,

                        performance.piSafetyLevel,
                        performance.piUsabilityAndGoalLevel,

                        performance.piMaintenanceDelay,
                        performance.piManagement,
                        performance.piAgency,
                        performance.piAADT,

                        performance.piBusiness,
                        performance.piBusinessType,
                        performance.piTargetAbsence,
                        performance.piBusinessClassification,
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

                        performance.piInputCount,
                        performance.piInputGreat,
                        performance.piInputMiddleSave,

                        performance.insertDateTime,
                        performance.insert_id,
                        performance.modifyDateTime,
                        performance.modify_id
                ));

        // 검색조건필터
        query.where(performance.insert_id.eq(insert_id));
        query.where(performance.piInputMiddleSave.eq(0));
        query.where(performance.piAutoNum.eq(autoNum));
        query.where(performance.piInputCount.eq(1));

        return query.fetchOne();
    }

    @Override
    public PerformancePiBusinessDto findByInsertIAndAutoNumAndCount(String insert_id, String autoNum, int count){

        QPerformance performance = QPerformance.performance;

        JPQLQuery<PerformancePiBusinessDto> query = from(performance)
                .select(Projections.constructor(PerformancePiBusinessDto.class,
                        performance.piFacilityType,
                        performance.piBusiness
                ));

        // 검색조건필터
        query.where(performance.insert_id.eq(insert_id));
        query.where(performance.piInputMiddleSave.eq(0));
        query.where(performance.piAutoNum.eq(autoNum));
        query.where(performance.piInputCount.eq(count));

        return query.fetchOne();

    }

    @Override
    public Page<PerformanceListDto> findByPerformanceList(String piFacilityType, String piKind, String piFacilityName, String insert_id, Pageable pageable) {

        QPerformance performance  = QPerformance.performance;

        JPQLQuery<PerformanceListDto> query = from(performance)
                .select(Projections.constructor(PerformanceListDto.class,
                        performance.piAutoNum,
                        performance.piFacilityType,
                        performance.piFacilityName,
                        performance.piCompletionYear,
                        performance.piErectionCost,
                        performance.piSafetyLevel,
                        performance.piUsabilityAndGoalLevel,
                        performance.piBusinessType,
                        performance.piBusinessExpenses
                ));

        if (!piFacilityType.equals("")){
            query.where(performance.piFacilityType.eq(piFacilityType));
        }
        if (!piKind.equals("")){
            query.where(performance.piKind.eq(piKind));
        }
        if (!piFacilityName.equals("")){
            query.where(performance.piFacilityName.containsIgnoreCase(piFacilityName));
        }

        // 필수조건
        query.where(performance.insert_id.eq(insert_id)); // 1. 현재 로그인한 유저가 등록한 글만 볼수있게 한다.
        query.where(performance.piInputGreat.eq(1)); // 2. 우수 대안만 리스트로 출력한다.
        query.where(performance.piInputMiddleSave.eq(1)); // 3. 중간저장된 글이 아닌 완전히 작성된 대안만 출력한다.

        query.orderBy(performance.id.desc());

        final List<PerformanceListDto> performanceListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(performanceListDtos, pageable, query.fetchCount());
    }

}
