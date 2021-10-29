package com.broadwave.backend.performance;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-07-29
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 PerformanceMiddleBusinessDataDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class PerformanceMiddleBusinessDataDto {

    private Long id;

    private String piBusinessType; // 사업유형(NOTNULL)
    private String piTargetAbsence; // 대상부재(NULL)
    private String piBusinessClassification; // 사업분류(NOTNULL)
    private Long piBusinessExpenses; // 사업비용(NOTNULL)

    private String piBeforeSafetyRating; // 사업전 부재 안전등급(NOTNULL)
    private String piAfterSafetyRating; // 사업후 부재 안전등급(NOTNULL)

    private Double piBusinessObligatory; // 법에 따른 의무사업(NOTNULL)
    private Double piBusinessMandatory; // 법정계획에 따른 의무사업(NOTNULL)
    private Double piBusinessPlanned; // 자체계획/의결에 따른 사업(NOTNULL)
    private Double piWhether; // 최근 1년간 민원 및 사고발생 건수(NOTNULL)

}
