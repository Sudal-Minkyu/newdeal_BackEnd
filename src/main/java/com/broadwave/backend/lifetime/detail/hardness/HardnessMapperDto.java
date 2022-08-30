package com.broadwave.backend.lifetime.detail.hardness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-07-14
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 반발경도 MapperDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HardnessMapperDto {

    private String ltDetailAutonum; // 고유코드
    private String ltDetailType; // 입력타입 : 1 반발경도, 2 탄산화깊이, 3 균열깊이, 4 열화물침투량

    private Double ltFdAverage; // 설계압축강도 평균값
    private Double ltFdStandard; // 설계압축강도 표준편차
    private Double ltFdVariance; // 설계압축강도 변동계수

    private Double ltYserviceAverage; // 공용연수 평균값
    private Double ltYserviceStandard; // 공용연수 표준편차
    private Double ltYserviceVariance; // 공용연수 변동계수

    private Double ltYAverage; // 노후재령계수 평균값
    private Double ltYStandard; // 노후재령계수 표준편차
    private Double ltYVariance; // 노후재령계수 변동계수

    private Double ltSAverage; // 하면 반발경도 평균값
    private Double ltSStandard; // 하면 반발경도 표준편차
    private Double ltSVariance; // 하면 반발경도 변동계수

    private Integer ltSimulation; // 시뮬레이션 횟수
    private Double ltRepairLength; // 보수보강 총길이
    private Double ltTargetValue; // 생애주기 목표값
    private Double ltRecoveryPercent; // 보수보강 회복율

    private Double ltRecoveryOne; // 보수보강 회복율_1
    private Double ltRecoveryTwo; // 보수보강 회복율_2
    private Double ltRecoveryThree; // 보수보강 회복율_3
    private Double ltRecoveryFour; // 보수보강 회복율_4
    private Double ltRecoveryFive; // 보수보강 회복율_5
    private Double ltRecoverySix; // 보수보강 회복율_6

    private Double ltCostOne; // 보수보강 비용_1
    private Double ltCostTwo; // 보수보강 비용_2
    private Double ltCostThree; // 보수보강 비용_3
    private Double ltCostFour; // 보수보강 비용_4
    private Double ltCostFive; // 보수보강 비용_5
    private Double ltCostSix; // 보수보강비용_6

}
