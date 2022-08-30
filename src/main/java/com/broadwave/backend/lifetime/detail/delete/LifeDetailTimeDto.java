package com.broadwave.backend.lifetime.detail.delete;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Minkyu
 * Date : 2021-09-15
 * Time :
 * Remark : 뉴딜 생애주기 의사결정 지원서비스 세부부분 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LifeDetailTimeDto {

    private Long id;

    private Double ltFyAverage; // 철근 항복강도 평균값(NOTNULL)
    private Double ltFyStandard; // 철근 항복강도 표준편차(NOTNULL)
    private Double ltFyVariance; // 철근 항복강도 변동계수(NOTNULL)

    private Double ltFcAverage; // 콘크리트 압축강도 평균값(NOTNULL)
    private Double ltFcStandard; // 콘크리트 압축강도 표준편차(NOTNULL)
    private Double ltFcVariance; // 콘크리트 압축강도 변동계수(NOTNULL)

    private Double ltSectionAverage; // 철근 단면적 평균값(NOTNULL)
    private Double ltSectionStandard; // 철근 단면적 표준편차(NOTNULL)
    private Double ltSectionVariance; // 철근 단면적 변동계수(NOTNULL)

    private Double ltVehicleAverage; // 차량하중 평균값(NOTNULL)
    private Double ltVehicleStandard; // 차량하중 표준편차(NOTNULL)
    private Double ltVehicleVariance; // 차량하중 변동계수(NOTNULL)

    private Double ltRepairLength; // 보수보강 총길이(NOTNULL)
    private Double ltTargetValue; // 생애주기 목표값(NOTNULL)
    private Double ltRecoveryPercent; // 보수보강 회복율(NOTNULL)

    private Double ltRecoveryOne; // 보수보강 회복율_1(NOTNULL)
    private Double ltRecoveryTwo; // 보수보강 회복율_2(NOTNULL)
    private Double ltRecoveryThree; // 보수보강 회복율_3(NOTNULL)
    private Double ltRecoveryFour; // 보수보강 회복율_4(NOTNULL)
    private Double ltRecoveryFive; // 보수보강 회복율_5(NOTNULL)
    private Double ltRecoverySix; // 보수보강 회복율_6(NOTNULL)

    private Double ltCostOne; // 보수보강 비용_1(NOTNULL)
    private Double ltCostTwo; // 보수보강 비용_2(NOTNULL)
    private Double ltCostThree; // 보수보강 비용_3(NOTNULL)
    private Double ltCostFour; // 보수보강 비용_4(NOTNULL)
    private Double ltCostFive; // 보수보강 비용_5(NOTNULL)
    private Double ltCostSix; // 보수보강비용_6(NOTNULL)

}
