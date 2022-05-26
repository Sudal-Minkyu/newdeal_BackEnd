package com.broadwave.backend.lifetime.detail.carbonation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-05-23
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 탄산화깊이 MapperDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CabonationMapperDto {

    private String ltDetailAutonum; // 고유코드
    private String ltDetailType; // 입력타입 : 1 반발경도, 2 탄산화깊이, 3 균열깊이, 4 열화물침투량

    private Double ltTdAverage; // 실측피복두께 평균값
    private Double ltTdStandard; // 실측피복두께 표준편차
    private Double ltTdVariance; // 실측피복두께 변동계수

    private Double ltYAverage; // 공용연수 평균값
    private Double ltYStandard; // 공용연수 표준편차
    private Double ltYVariance; // 공용연수 변동계수

    private Double ltAAverage; // 탄산화속도계수 평균값
    private Double ltAStandard; // 탄산화속도계수 표준편차
    private Double ltAVariance; // 탄산화속도계수 변동계수

    private Double ltCAverage; // 탄산화깊이 평균값
    private Double ltCStandard; // 탄산화깊이 표준편차
    private Double ltCVariance; // 탄산화깊이 변동계수

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
