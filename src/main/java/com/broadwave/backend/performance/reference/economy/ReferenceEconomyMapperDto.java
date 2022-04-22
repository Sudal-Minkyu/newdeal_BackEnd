package com.broadwave.backend.performance.reference.economy;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-11-02
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 결제성 Dto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReferenceEconomyMapperDto {

    private Double piEcoAssetAMin; // 자산가치 개선 효율성 A 최소값
    private Double piEcoAssetAScore; // 자산가치 개선 효율성 A 환산점수
    private Double piEcoAssetBMin; // 자산가치 개선 효율성 B 최소값
    private Double piEcoAssetBMax; // 자산가치 개선 효율성 B 최대값
    private Double piEcoAssetBScore; // 자산가치 개선 효율성 B 환산점수
    private Double piEcoAssetCMin; // 자산가치 개선 효율성 C 최소값
    private Double piEcoAssetCMax; // 자산가치 개선 효율성 C 최대값
    private Double piEcoAssetCScore; // 자산가치 개선 효율성 C 환산점수
    private Double piEcoAssetDMin; // 자산가치 개선 효율성 D 최소값
    private Double piEcoAssetDMax; // 자산가치 개선 효율성 D 최대값
    private Double piEcoAssetDScore; // 자산가치 개선 효율성 D 환산점수
    private Double piEcoAssetEMax; // 자산가치 개선 효율성 E 최대값
    private Double piEcoAssetEScore; // 자산가치 개선 효율성 E 환산점수

    private Double piEcoLifeA; // 안전등급에 따른 잔존수명비율 환산표 A등급 잔존수명 비율
    private Double piEcoLifeB; // 안전등급에 따른 잔존수명비율 환산표 B등급 잔존수명 비율
    private Double piEcoLifeC; // 안전등급에 따른 잔존수명비율 환산표 C등급 잔존수명 비율
    private Double piEcoLifeD; // 안전등급에 따른 잔존수명비율 환산표 D등급 잔존수명 비율
    private Double piEcoLifeE; // 안전등급에 따른 잔존수명비율 환산표 E등급 잔존수명 비율

    private Double piEcoFacilityA; // 시설 유형별 내용연수 참조표 - 교량
    private Double piEcoFacilityB; // 시설 유형별 내용연수 참조표 - 보도육교
    private Double piEcoFacilityC; // 시설 유형별 내용연수 참조표 - 터널
    private Double piEcoFacilityD; // 시설 유형별 내용연수 참조표 - 지하터널
    private Double piEcoFacilityE; // 시설 유형별 내용연수 참조표 - 옹벽
    private Double piEcoFacilityF; // 시설 유형별 내용연수 참조표 - 절토사면
    private Double piEcoFacilityG; // 시설 유형별 내용연수 참조표 - 포장
    
    private Double piEcoUtilityAa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 A, 사업후등급 A 일때,
    private Double piEcoUtilityBa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 B, 사업후등급 A 일때,
    private Double piEcoUtilityBb; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 B, 사업후등급 B 일때,
    private Double piEcoUtilityCa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 C, 사업후등급 A 일때,
    private Double piEcoUtilityCb; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 C, 사업후등급 B 일때,
    private Double piEcoUtilityCc; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 C, 사업후등급 C 일때,
    private Double piEcoUtilityDa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 D, 사업후등급 A 일때,
    private Double piEcoUtilityDb; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 D, 사업후등급 B 일때,
    private Double piEcoUtilityDc; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 D, 사업후등급 C 일때,
    private Double piEcoUtilityDd; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 D, 사업후등급 D 일때,
    private Double piEcoUtilityEa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 A 일때,
    private Double piEcoUtilityEb; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 B 일때,
    private Double piEcoUtilityEc; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 C 일때,
    private Double piEcoUtilityEd; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 D 일때,
    private Double piEcoUtilityEe; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 E 일때,
    
    private Double piEcoTrafficAMin; // 교통량 등급에 따른 가중계수표 A 최소값
    private Double piEcoTrafficAScore; // 교통량 등급에 따른 가중계수표 A 가중계수
    private Double piEcoTrafficBMin; // 교통량 등급에 따른 가중계수표 B 최소값
    private Double piEcoTrafficBMax; // 교통량 등급에 따른 가중계수표 B 최대값
    private Double piEcoTrafficBScore; // 교통량 등급에 따른 가중계수표 B 가중계수
    private Double piEcoTrafficCMin; // 교통량 등급에 따른 가중계수표 C 최소값
    private Double piEcoTrafficCMax; // 교통량 등급에 따른 가중계수표 C 최대값
    private Double piEcoTrafficCScore; // 교통량 등급에 따른 가중계수표 C 가중계수
    private Double piEcoTrafficDMin; // 교통량 등급에 따른 가중계수표 D 최소값
    private Double piEcoTrafficDMax; // 교통량 등급에 따른 가중계수표 D 최대값
    private Double piEcoTrafficDScore; // 교통량 등급에 따른 가중계수표 D 가중계수
    private Double piEcoTrafficEMax; // 교통량 등급에 따른 가중계수표 E 최대값
    private Double piEcoTrafficEScore; // 교통량 등급에 따른 가중계수표 E 가중계수
    
    private Double piEcoImproAMin; // 안전효용 개선 효율성 A 최소값
    private Double piEcoImproAScore; // 안전효용 개선 효율성 A 환산점수
    private Double piEcoImproBMin; // 안전효용 개선 효율성 B 최소값
    private Double piEcoImproBMax; // 안전효용 개선 효율성 B 최대값
    private Double piEcoImproBScore; // 안전효용 개선 효율성 B 환산점수
    private Double piEcoImproCMin; // 안전효용 개선 효율성 C 최소값
    private Double piEcoImproCMax; // 안전효용 개선 효율성 C 최대값
    private Double piEcoImproCScore; // 안전효용 개선 효율성 C 환산점수
    private Double piEcoImproDMin; // 안전효용 개선 효율성 D 최소값
    private Double piEcoImproDMax; // 안전효용 개선 효율성 D 최대값
    private Double piEcoImproDScore; // 안전효용 개선 효율성 D 환산점수
    private Double piEcoImproEMax; // 안전효용 개선 효율성 E 최대값
    private Double piEcoImproEScore; // 안전효용 개선 효율성 E 환산점수
    
    private Double piEcoScaleBaseA; // 사업규모 등급 A 평가기준
    private Double piEcoScaleBaseB; // 사업규모 등급 B 평가기준
    private Double piEcoScaleBaseC; // 사업규모 등급 C 평가기준
    private Double piEcoScaleBaseD; // 사업규모 등급 D 평가기준
    private Double piEcoScaleBaseE; // 사업규모 등급 E 평가기준
    private Double piEcoScaleScoreA; // 사업규모 등급 A 환산점수
    private Double piEcoScaleScoreB; // 사업규모 등급 B 환산점수
    private Double piEcoScaleScoreC; // 사업규모 등급 C 환산점수
    private Double piEcoScaleScoreD; // 사업규모 등급 D 환산점수
    private Double piEcoScaleScoreE; // 사업규모 등급 E환산점수
    
    private Double piEcoEfficiencyBaseA; // 사업효율 등급 A 평가기준
    private Double piEcoEfficiencyBaseB; // 사업효율 등급 B 평가기준
    private Double piEcoEfficiencyBaseC; // 사업효율 등급 C 평가기준
    private Double piEcoEfficiencyBaseD; // 사업효율 등급 D 평가기준
    private Double piEcoEfficiencyBaseE; // 사업효율 등급 E 평가기준
    private Double piEcoEfficiencyScoreA; // 사업효율 등급 A 환산점수
    private Double piEcoEfficiencyScoreB; // 사업효율 등급 B 환산점수
    private Double piEcoEfficiencyScoreC; // 사업효율 등급 C 환산점수
    private Double piEcoEfficiencyScoreD; // 사업효율 등급 D 환산점수
    private Double piEcoEfficiencyScoreE; // 사업효율 등급 E 환산점수
    
    private Double piEcoGoalAPlusMin; // 종합평가 등급 환산표 A+ 등급 최소점수
    private Double piEcoGoalAPlusMax; // 종합평가 등급 환산표 A+ 등급 최대점수
    private Double piEcoGoalAMinusMin; // 종합평가 등급 환산표 A- 등급 최소점수
    private Double piEcoGoalAMinusMax; // 종합평가 등급 환산표 A- 등급 최대점수
    private Double piEcoGoalBPlusMin; // 종합평가 등급 환산표 B+ 등급 최소점수
    private Double piEcoGoalBPlusMax; // 종합평가 등급 환산표 B+ 등급 최대점수
    private Double piEcoGoalBMinusMin; // 종합평가 등급 환산표 B- 등급 최소점수
    private Double piEcoGoalBMinusMax; // 종합평가 등급 환산표 B- 등급 최대점수
    private Double piEcoGoalCPlusMin; // 종합평가 등급 환산표 C+ 등급 최소점수
    private Double piEcoGoalCPlusMax; // 종합평가 등급 환산표 C+ 등급 최대점수
    private Double piEcoGoalCMinusMin; // 종합평가 등급 환산표 C- 등급 최소점수
    private Double piEcoGoalCMinusMax; // 종합평가 등급 환산표 C- 등급 최대점수
    private Double piEcoGoalDPlusMin; // 종합평가 등급 환산표 D+ 등급 최소점수
    private Double piEcoGoalDPlusMax; // 종합평가 등급 환산표 D+ 등급 최대점수
    private Double piEcoGoalDMinusMin; // 종합평가 등급 환산표 D- 등급 최소점수
    private Double piEcoGoalDMinusMax; // 종합평가 등급 환산표 D- 등급 최대점수
    private Double piEcoGoalEMax; // 종합평가 등급 환산표 E 등급 최소점수

}
