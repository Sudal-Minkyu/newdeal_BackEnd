package com.broadwave.backend.performance.reference.economy;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-10-29
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 경제성
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_pi_economy")
public class ReferenceEconomy {

    @Id
    @Column(name="pi_eco_id")
    private String id; // 고유ID값(NOTNULL)


    @Column(name="pi_eco_asset_a_min")
    private Double piEcoAssetAMin; // 자산가치 개선 효율성 A 최소값

    @Column(name="pi_eco_asset_a_score")
    private Double piEcoAssetAScore; // 자산가치 개선 효율성 A 환산점수

    @Column(name="pi_eco_asset_b_min")
    private Double piEcoAssetBMin; // 자산가치 개선 효율성 B 최소값

    @Column(name="pi_eco_asset_b_max")
    private Double piEcoAssetBMax; // 자산가치 개선 효율성 B 최대값

    @Column(name="pi_eco_asset_b_score")
    private Double piEcoAssetBScore; // 자산가치 개선 효율성 B 환산점수

    @Column(name="pi_eco_asset_c_min")
    private Double piEcoAssetCMin; // 자산가치 개선 효율성 C 최소값

    @Column(name="pi_eco_asset_c_max")
    private Double piEcoAssetCMax; // 자산가치 개선 효율성 C 최대값

    @Column(name="pi_eco_asset_c_score")
    private Double piEcoAssetCScore; // 자산가치 개선 효율성 C 환산점수

    @Column(name="pi_eco_asset_d_min")
    private Double piEcoAssetDMin; // 자산가치 개선 효율성 D 최소값

    @Column(name="pi_eco_asset_d_max")
    private Double piEcoAssetDMax; // 자산가치 개선 효율성 D 최대값

    @Column(name="pi_eco_asset_d_score")
    private Double piEcoAssetDScore; // 자산가치 개선 효율성 D 환산점수

    @Column(name="pi_eco_asset_e_max")
    private Double piEcoAssetEMax; // 자산가치 개선 효율성 E 최대값

    @Column(name="pi_eco_asset_e_score")
    private Double piEcoAssetEScore; // 자산가치 개선 효율성 E 환산점수



    @Column(name="pi_eco_life_a")
    private Double piEcoLifeA; // 안전등급에 따른 잔존수명비율 환산표 A등급 잔존수명 비율

    @Column(name="pi_eco_life_b")
    private Double piEcoLifeB; // 안전등급에 따른 잔존수명비율 환산표 B등급 잔존수명 비율

    @Column(name="pi_eco_life_c")
    private Double piEcoLifeC; // 안전등급에 따른 잔존수명비율 환산표 C등급 잔존수명 비율

    @Column(name="pi_eco_life_d")
    private Double piEcoLifeD; // 안전등급에 따른 잔존수명비율 환산표 D등급 잔존수명 비율

    @Column(name="pi_eco_life_e")
    private Double piEcoLifeE; // 안전등급에 따른 잔존수명비율 환산표 E등급 잔존수명 비율



    @Column(name="pi_eco_facility_a")
    private Double piEcoFacilityA; // 시설 유형별 내용연수 참조표 - 교량

    @Column(name="pi_eco_facility_b")
    private Double piEcoFacilityB; // 시설 유형별 내용연수 참조표 - 보도육교

    @Column(name="pi_eco_facility_c")
    private Double piEcoFacilityC; // 시설 유형별 내용연수 참조표 - 터널

    @Column(name="pi_eco_facility_d")
    private Double piEcoFacilityD; // 시설 유형별 내용연수 참조표 - 지하터널

    @Column(name="pi_eco_facility_e")
    private Double piEcoFacilityE; // 시설 유형별 내용연수 참조표 - 옹벽

    @Column(name="pi_eco_facility_f")
    private Double piEcoFacilityF; // 시설 유형별 내용연수 참조표 - 절토사면

    @Column(name="pi_eco_facility_g")
    private Double piEcoFacilityG; // 시설 유형별 내용연수 참조표 - 포장



    @Column(name="pi_eco_utility_aa")
    private Double piEcoUtilityAa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 A, 사업후등급 A 일때,

    @Column(name="pi_eco_utility_ba")
    private Double piEcoUtilityBa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 B, 사업후등급 A 일때,

    @Column(name="pi_eco_utility_bb")
    private Double piEcoUtilityBb; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 B, 사업후등급 B 일때,

    @Column(name="pi_eco_utility_ca")
    private Double piEcoUtilityCa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 C, 사업후등급 A 일때,

    @Column(name="pi_eco_utility_cb")
    private Double piEcoUtilityCb; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 C, 사업후등급 B 일때,

    @Column(name="pi_eco_utility_cc")
    private Double piEcoUtilityCc; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 C, 사업후등급 C 일때,

    @Column(name="pi_eco_utility_da")
    private Double piEcoUtilityDa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 D, 사업후등급 A 일때,

    @Column(name="pi_eco_utility_db")
    private Double piEcoUtilityDb; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 D, 사업후등급 B 일때,

    @Column(name="pi_eco_utility_dc")
    private Double piEcoUtilityDc; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 D, 사업후등급 C 일때,

    @Column(name="pi_eco_utility_dd")
    private Double piEcoUtilityDd; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 D, 사업후등급 D 일때,

    @Column(name="pi_eco_utility_ea")
    private Double piEcoUtilityEa; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 A 일때,

    @Column(name="pi_eco_utility_eb")
    private Double piEcoUtilityEb; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 B 일때,

    @Column(name="pi_eco_utility_ec")
    private Double piEcoUtilityEc; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 C 일때,

    @Column(name="pi_eco_utility_ed")
    private Double piEcoUtilityEd; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 D 일때,

    @Column(name="pi_eco_utility_ee")
    private Double piEcoUtilityEe; // 사업 전후 안전등급에 따른 효용성 점수표 사업전등급 E, 사업후등급 E 일때,



    @Column(name="pi_eco_traffic_a_min")
    private Double piEcoTrafficAMin; // 교통량 등급에 따른 가중계수표 A 최소값

    @Column(name="pi_eco_traffic_a_score")
    private Double piEcoTrafficAScore; // 교통량 등급에 따른 가중계수표 A 가중계수

    @Column(name="pi_eco_traffic_b_min")
    private Double piEcoTrafficBMin; // 교통량 등급에 따른 가중계수표 B 최소값

    @Column(name="pi_eco_traffic_b_max")
    private Double piEcoTrafficBMax; // 교통량 등급에 따른 가중계수표 B 최대값

    @Column(name="pi_eco_traffic_b_score")
    private Double piEcoTrafficBScore; // 교통량 등급에 따른 가중계수표 B 가중계수

    @Column(name="pi_eco_traffic_c_min")
    private Double piEcoTrafficCMin; // 교통량 등급에 따른 가중계수표 C 최소값

    @Column(name="pi_eco_traffic_c_max")
    private Double piEcoTrafficCMax; // 교통량 등급에 따른 가중계수표 C 최대값

    @Column(name="pi_eco_traffic_c_score")
    private Double piEcoTrafficCScore; // 교통량 등급에 따른 가중계수표 C 가중계수

    @Column(name="pi_eco_traffic_d_min")
    private Double piEcoTrafficDMin; // 교통량 등급에 따른 가중계수표 D 최소값

    @Column(name="pi_eco_traffic_d_max")
    private Double piEcoTrafficDMax; // 교통량 등급에 따른 가중계수표 D 최대값

    @Column(name="pi_eco_traffic_d_score")
    private Double piEcoTrafficDScore; // 교통량 등급에 따른 가중계수표 D 가중계수

    @Column(name="pi_eco_traffic_e_max")
    private Double piEcoTrafficEMax; // 교통량 등급에 따른 가중계수표 E 최대값

    @Column(name="pi_eco_traffic_e_score")
    private Double piEcoTrafficEScore; // 교통량 등급에 따른 가중계수표 E 가중계수



    @Column(name="pi_eco_impro_a_min")
    private Double piEcoImproAMin; // 안전효용 개선 효율성 A 최소값

    @Column(name="pi_eco_impro_a_score")
    private Double piEcoImproAScore; // 안전효용 개선 효율성 A 환산점수

    @Column(name="pi_eco_impro_b_min")
    private Double piEcoImproBMin; // 안전효용 개선 효율성 B 최소값

    @Column(name="pi_eco_impro_b_max")
    private Double piEcoImproBMax; // 안전효용 개선 효율성 B 최대값

    @Column(name="pi_eco_impro_b_score")
    private Double piEcoImproBScore; // 안전효용 개선 효율성 B 환산점수

    @Column(name="pi_eco_impro_c_min")
    private Double piEcoImproCMin; // 안전효용 개선 효율성 C 최소값

    @Column(name="pi_eco_impro_c_max")
    private Double piEcoImproCMax; // 안전효용 개선 효율성 C 최대값

    @Column(name="pi_eco_impro_c_score")
    private Double piEcoImproCScore; // 안전효용 개선 효율성 C 환산점수

    @Column(name="pi_eco_impro_d_min")
    private Double piEcoImproDMin; // 안전효용 개선 효율성 D 최소값

    @Column(name="pi_eco_impro_d_max")
    private Double piEcoImproDMax; // 안전효용 개선 효율성 D 최대값

    @Column(name="pi_eco_impro_d_score")
    private Double piEcoImproDScore; // 안전효용 개선 효율성 D 환산점수

    @Column(name="pi_eco_impro_e_max")
    private Double piEcoImproEMax; // 안전효용 개선 효율성 E 최대값

    @Column(name="pi_eco_impro_e_score")
    private Double piEcoImproEScore; // 안전효용 개선 효율성 E 환산점수


    @Column(name="pi_eco_scale_base_a")
    private Double piEcoScaleBaseA; // 사업규모 등급 A 평가기준

    @Column(name="pi_eco_scale_base_b")
    private Double piEcoScaleBaseB; // 사업규모 등급 B 평가기준

    @Column(name="pi_eco_scale_base_c")
    private Double piEcoScaleBaseC; // 사업규모 등급 C 평가기준

    @Column(name="pi_eco_scale_base_d")
    private Double piEcoScaleBaseD; // 사업규모 등급 D 평가기준

    @Column(name="pi_eco_scale_base_e")
    private Double piEcoScaleBaseE; // 사업규모 등급 E 평가기준

    @Column(name="pi_eco_scale_score_a")
    private Double piEcoScaleScoreA; // 사업규모 등급 A 환산점수

    @Column(name="pi_eco_scale_score_b")
    private Double piEcoScaleScoreB; // 사업규모 등급 B 환산점수

    @Column(name="pi_eco_scale_score_c")
    private Double piEcoScaleScoreC; // 사업규모 등급 C 환산점수

    @Column(name="pi_eco_scale_score_d")
    private Double piEcoScaleScoreD; // 사업규모 등급 D 환산점수

    @Column(name="pi_eco_scale_score_e")
    private Double piEcoScaleScoreE; // 사업규모 등급 E환산점수



    @Column(name="pi_eco_efficiency_base_a")
    private Double piEcoEfficiencyBaseA; // 사업효율 등급 A 평가기준

    @Column(name="pi_eco_efficiency_base_b")
    private Double piEcoEfficiencyBaseB; // 사업효율 등급 B 평가기준

    @Column(name="pi_eco_efficiency_base_c")
    private Double piEcoEfficiencyBaseC; // 사업효율 등급 C 평가기준

    @Column(name="pi_eco_efficiency_base_d")
    private Double piEcoEfficiencyBaseD; // 사업효율 등급 D 평가기준

    @Column(name="pi_eco_efficiency_base_e")
    private Double piEcoEfficiencyBaseE; // 사업효율 등급 E 평가기준

    @Column(name="pi_eco_efficiency_score_a")
    private Double piEcoEfficiencyScoreA; // 사업효율 등급 A 환산점수

    @Column(name="pi_eco_efficiency_score_b")
    private Double piEcoEfficiencyScoreB; // 사업효율 등급 B 환산점수

    @Column(name="pi_eco_efficiency_score_c")
    private Double piEcoEfficiencyScoreC; // 사업효율 등급 C 환산점수

    @Column(name="pi_eco_efficiency_score_d")
    private Double piEcoEfficiencyScoreD; // 사업효율 등급 D 환산점수

    @Column(name="pi_eco_efficiency_score_e")
    private Double piEcoEfficiencyScoreE; // 사업효율 등급 E 환산점수



    @Column(name="pi_eco_goal_a_plus_min")
    private Double piEcoGoalAPlusMin; // 종합평가 등급 환산표 A+ 등급 최소점수

    @Column(name="pi_eco_goal_a_plus_max")
    private Double piEcoGoalAPlusMax; // 종합평가 등급 환산표 A+ 등급 최대점수

    @Column(name="pi_eco_goal_a_minus_min")
    private Double piEcoGoalAMinusMin; // 종합평가 등급 환산표 A- 등급 최소점수

    @Column(name="pi_eco_goal_a_minus_max")
    private Double piEcoGoalAMinusMax; // 종합평가 등급 환산표 A- 등급 최대점수

    @Column(name="pi_eco_goal_b_plus_min")
    private Double piEcoGoalBPlusMin; // 종합평가 등급 환산표 B+ 등급 최소점수

    @Column(name="pi_eco_goal_b_plus_max")
    private Double piEcoGoalBPlusMax; // 종합평가 등급 환산표 B+ 등급 최대점수

    @Column(name="pi_eco_goal_b_minus_min")
    private Double piEcoGoalBMinusMin; // 종합평가 등급 환산표 B- 등급 최소점수

    @Column(name="pi_eco_goal_b_minus_max")
    private Double piEcoGoalBMinusMax; // 종합평가 등급 환산표 B- 등급 최대점수

    @Column(name="pi_eco_goal_c_plus_min")
    private Double piEcoGoalCPlusMin; // 종합평가 등급 환산표 C+ 등급 최소점수

    @Column(name="pi_eco_goal_c_plus_max")
    private Double piEcoGoalCPlusMax; // 종합평가 등급 환산표 C+ 등급 최대점수

    @Column(name="pi_eco_goal_c_minus_min")
    private Double piEcoGoalCMinusMin; // 종합평가 등급 환산표 C- 등급 최소점수

    @Column(name="pi_eco_goal_c_minus_max")
    private Double piEcoGoalCMinusMax; // 종합평가 등급 환산표 C- 등급 최대점수

    @Column(name="pi_eco_goal_d_plus_min")
    private Double piEcoGoalDPlusMin; // 종합평가 등급 환산표 D+ 등급 최소점수

    @Column(name="pi_eco_goal_d_plus_max")
    private Double piEcoGoalDPlusMax; // 종합평가 등급 환산표 D+ 등급 최대점수

    @Column(name="pi_eco_goal_d_minus_min")
    private Double piEcoGoalDMinusMin; // 종합평가 등급 환산표 D- 등급 최소점수

    @Column(name="pi_eco_goal_d_minus_max")
    private Double piEcoGoalDMinusMax; // 종합평가 등급 환산표 D- 등급 최대점수

    @Column(name="pi_eco_goal_e_max")
    private Double piEcoGoalEMax; // 종합평가 등급 환산표 E 등급 최대점수



    @Column(name="insert_date")
    private LocalDateTime insertDateTime; // 저장날짜

    @Column(name="insert_id")
    private String insert_id; // 저장 사용자ID

}
