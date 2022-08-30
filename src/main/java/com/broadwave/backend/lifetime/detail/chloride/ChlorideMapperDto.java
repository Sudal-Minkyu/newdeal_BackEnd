package com.broadwave.backend.lifetime.detail.chloride;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-08-04
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 열화물침투량 MapperDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChlorideMapperDto {

    private String ltDetailAutonum; // 고유코드
    private String ltDetailType; // 입력타입 : 1 반발경도, 2 탄산화깊이, 3 균열깊이, 4 열화물침투량

    private Double ltCoAverage; // 표면 염화물함유량 평균값
    private Double ltCoStandard; // 표면 염화물함유량 표준편차
    private Double ltCoVariance; // 표면 염화물함유량 변동계수

    private Double ltClimAverage; // 임계 염화물함유량 평균값
    private Double ltClimStandard; // 임계 염화물함유량 표준편차
    private Double ltClimVariance; // 임계 염화물함유량 변동계수

    private Double ltDAverage; // 염소이온 확산계수 평균값
    private Double ltDStandard; // 염소이온 확산계수 표준편차
    private Double ltDVariance; // 염소이온 확산계수 변동계수

    private Double ltXAverage; // 피복두께 평균값
    private Double ltXStandard; // 피복두께 표준편차
    private Double ltXVariance; // 피복두께 변동계수

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
