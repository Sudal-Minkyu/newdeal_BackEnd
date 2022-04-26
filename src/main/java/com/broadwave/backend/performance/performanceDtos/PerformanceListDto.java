package com.broadwave.backend.performance.performanceDtos;

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
    private String piType; // 형식구분(NULL)
    private String piBusiness; // 사업구분(NOTNULL)
    private String piSafetyLevel; // 안전등급(NOTNULL)
    private String piKind; // 종별구분(NOTNULL)
    private Long piBusinessExpenses; // 사업비용(NOTNULL)
}
