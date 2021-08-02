package com.broadwave.backend.performance;

import lombok.*;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-08-02
 * Time :
 * Remark : 뉴딜 성능개선사업평가 PerformancePiBusinessDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class PerformancePiBusinessDto {
    private String piFacilityType; // 시설유형
    private String piBusiness; // 사업구분(NOTNULL)
}
