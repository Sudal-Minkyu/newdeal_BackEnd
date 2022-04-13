package com.broadwave.backend.performance.performanceDtos;

import lombok.*;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-07-23
 * Time :
 * Remark : 뉴딜 성능개선사업평가 중간저장 두번째 테이블
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class PerformanceMiddleSaveBusinessDto {

    private Integer businessCount;

    private String piBusinessType1; // 사업유형1(NOTNULL)
    private String piBusinessType2; // 사업유형2(NOTNULL)
    private String piBusinessType3; // 사업유형3(NOTNULL)
    private String piBusinessType4; // 사업유형4(NOTNULL)

    private List<String> piBusinessType; // 사업유형(NOTNULL)
    private List<String> piTargetAbsence; // 대상부재(NULL)
    private List<String> piBusinessClassification; // 사업분류(NOTNULL)
    private List<Long> piBusinessExpenses; // 사업비용(NOTNULL)

    private List<String> piBeforeSafetyRating; // 사업전 부재 안전등급(NOTNULL)
    private List<String> piAfterSafetyRating; // 사업후 부재 안전등급(NOTNULL)

    private Double piBusinessObligatory1; // 법에 따른 의무사업(NOTNULL)
    private Double piBusinessMandatory1; // 법정계획에 따른 의무사업(NOTNULL)
    private Double piBusinessPlanned1; // 자체계획/의결에 따른 사업(NOTNULL)

    private Double piBusinessObligatory2; // 법에 따른 의무사업(NOTNULL)
    private Double piBusinessMandatory2; // 법정계획에 따른 의무사업(NOTNULL)
    private Double piBusinessPlanned2; // 자체계획/의결에 따른 사업(NOTNULL)

    private Double piBusinessObligatory3; // 법에 따른 의무사업(NOTNULL)
    private Double piBusinessMandatory3; // 법정계획에 따른 의무사업(NOTNULL)
    private Double piBusinessPlanned3; // 자체계획/의결에 따른 사업(NOTNULL)

    private Double piBusinessObligatory4; // 법에 따른 의무사업(NOTNULL)
    private Double piBusinessMandatory4; // 법정계획에 따른 의무사업(NOTNULL)
    private Double piBusinessPlanned4; // 자체계획/의결에 따른 사업(NOTNULL)

    private Double piWhether; // 최근 1년간 민원 및 사고발생 건수(NOTNULL)

}
