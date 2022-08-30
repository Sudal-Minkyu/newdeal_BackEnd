package com.broadwave.backend.lifetime.detail.crack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @author Minkyu
 * Date : 2022-08-04
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 균열깊이 InfoDto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CrackInfoDto {

    private Double ltTdAverage; // 설계피복두깨 평균값
    private Double ltTdStandard; // 설계피복두깨 표준편차
    private Double ltTdVariance; // 설계피복두깨 변동계수

    private Double ltLAverage; // 발신수신자 사이의 거리 평균값
    private Double ltLStandard; // 발신수신자 사이의 거리 표준편차
    private Double ltLVariance; // 발신수신자 사이의 거리 변동계수

    private Double ltTcAverage; // 균열있는경우 전달시간 평균값
    private Double ltTcStandard; // 균열있는경우 전달시간 표준편차
    private Double ltTcVariance; // 균열있는경우 전달시간 변동계수

    private Double ltToAverage; // 균열없는경우 전달시간 평균값
    private Double ltToStandard; // 균열없는경우 전달시간 표준편차
    private Double ltToVariance; // 균열없는경우 전달시간 변동계수

    private Integer ltPublicYear; // 공용연수
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
