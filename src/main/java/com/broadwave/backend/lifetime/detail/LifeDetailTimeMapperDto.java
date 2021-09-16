package com.broadwave.backend.lifetime.detail;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;

/**
 * @author Minkyu
 * Date : 2021-09-14
 * Time :
 * Remark : 뉴딜 생애주기 의사결정 지원서비스 세부부분 MapperDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Slf4j
public class LifeDetailTimeMapperDto {

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

    private Double ltDetailLength; // 보수보강 총 길이(NOTNULL)
    private Integer ltDetailNum; // 보수보강 개입 횟수(NOTNULL)
    private Double ltDetailCost; // 보수보강 총 비용(NOTNULL)

    public Double getLtFyAverage() {
        return ltFyAverage;
    }

    public Double getLtFyStandard() {
        return ltFyStandard;
    }

    public Double getLtFyVariance() {
        return ltFyVariance/100;
    }

    public Double getLtFcAverage() {
        return ltFcAverage;
    }

    public Double getLtFcStandard() {
        return ltFcStandard;
    }

    public Double getLtFcVariance() {
        return ltFcVariance/100;
    }

    public Double getLtSectionAverage() {
        return ltSectionAverage;
    }

    public Double getLtSectionStandard() {
        return ltSectionStandard;
    }

    public Double getLtSectionVariance() {
        return ltSectionVariance/100;
    }

    public Double getLtVehicleAverage() {
        return ltVehicleAverage;
    }

    public Double getLtVehicleStandard() {
        return ltVehicleStandard;
    }

    public Double getLtVehicleVariance() {
        return ltVehicleVariance/100;
    }

    public Double getLtTargetValue() {
        return ltTargetValue;
    }

    public Double getLtRecoveryPercent() {
        return ltRecoveryPercent;
    }

    public Double getLtRecoveryOne() {
        return ltRecoveryOne;
    }

    public Double getLtRecoveryTwo() {
        return ltRecoveryTwo;
    }

    public Double getLtRecoveryThree() {
        return ltRecoveryThree;
    }

    public Double getLtRecoveryFour() {
        return ltRecoveryFour;
    }

    public Double getLtRecoveryFive() {
        return ltRecoveryFive;
    }

    public Double getLtRecoverySix() {
        return ltRecoverySix;
    }

    public Double getLtCostOne() {
        return ltCostOne;
    }

    public Double getLtCostTwo() {
        return ltCostTwo;
    }

    public Double getLtCostThree() {
        return ltCostThree;
    }

    public Double getLtCostFour() {
        return ltCostFour;
    }

    public Double getLtCostFive() {
        return ltCostFive;
    }

    public Double getLtCostSix() {
        return ltCostSix;
    }

    public Double getLtDetailLength() {
        return ltDetailLength;
    }

    public Integer getLtDetailNum() {
        return ltDetailNum;
    }

    public Double getLtDetailCost() {
        return ltDetailCost;
    }

}
