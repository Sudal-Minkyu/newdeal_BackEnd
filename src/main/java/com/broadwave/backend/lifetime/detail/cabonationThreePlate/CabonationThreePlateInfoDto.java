package com.broadwave.backend.lifetime.detail.cabonationThreePlate;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2022-06-29
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 탄산화깊이 바닥판 3개 InfoDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CabonationThreePlateInfoDto {

    private Double ltTdAveragePlate1; // 바닥판1의 실측피복두께 평균값
    private Double ltTdStandardPlate1; // 바닥판1의 실측피복두께 표준편차
    private Double ltAAveragePlate1; // 바닥판1의 탄산화속도계수 평균값
    private Double ltAStandardPlate1; // 바닥판1의 탄산화속도계수 표준편차
    private Double ltCAveragePlate1; // 바닥판1의 탄산화깊이 평균값
    private Double ltCStandardPlate1; // 바닥판1의 탄산화깊이 표준편차

    private Double ltTdAveragePlate2; // 바닥판2의 실측피복두께 평균값
    private Double ltTdStandardPlate2; // 바닥판2의 실측피복두께 표준편차
    private Double ltAAveragePlate2; // 바닥판2의 탄산화속도계수 평균값
    private Double ltAStandardPlate2; // 바닥판2의 탄산화속도계수 표준편차
    private Double ltCAveragePlate2; // 바닥판2의 탄산화깊이 평균값
    private Double ltCStandardPlate2; // 바닥판2의 탄산화깊이 표준편차

    private Double ltTdAveragePlate3; // 바닥판3의 실측피복두께 평균값
    private Double ltTdStandardPlate3; // 바닥판3의 실측피복두께 표준편차
    private Double ltAAveragePlate3; // 바닥판3의 탄산화속도계수 평균값
    private Double ltAStandardPlate3; // 바닥판3의 탄산화속도계수 표준편차
    private Double ltCAveragePlate3; // 바닥판3의 탄산화깊이 평균값
    private Double ltCStandardPlate3; // 바닥판3의 탄산화깊이 표준편차

    private Double ltYAverage; // 공용연수 조정계수 평균값
    private Double ltYStandard; // 공용연수 조정계수 표준편차
    private Integer ltPublicYear; // 입력 공용연수
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
