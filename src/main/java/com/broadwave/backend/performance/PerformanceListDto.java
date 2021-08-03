package com.broadwave.backend.performance;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Time :
 * Remark : 뉴딜 성능개선사업평가 조회 PerformanceListDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class PerformanceListDto {
    private String piAutoNum; // 일련번호(NOTNULL)
    private String piFacilityType; // 시설유형(NOTNULL)
    private String piFacilityName; // 시설명(NULL)
    private Double piCompletionYear; // 준공연도(NOTNULL)
    private Long piErectionCost; // 취득원가(NOTNULL)
    private String piSafetyLevel; // 안전등급(NOTNULL)
    private String piGoalLevel; // 목표등급(NOTNULL)
    private String piBusinessType; // 사업유형(NOTNULL)
    private Long piBusinessExpenses; // 사업비용(NOTNULL)
}
