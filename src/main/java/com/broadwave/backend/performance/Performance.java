package com.broadwave.backend.performance;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-07-07
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사)
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_pi_input")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pi_id")
    private Long id; // 고유ID값(NOTNULL)

    @Column(name="pi_auto_num")
    private String piAutoNum; // 대안 일려번호(NOTNULL)

    @Column(name="pi_facility_type")
    private String piFacilityType; // 시설유형(NOTNULL)

    @Column(name="pi_facility_name")
    private String piFacilityName; // 시설명(NULL)

    @Column(name="pi_kind")
    private String piKind; // 종별구분(NOTNULL)

    @Column(name="pi_completion_year")
    private Double piCompletionYear; // 준공연도(NOTNULL)

    @Column(name="pi_public_year")
    private Double piPublicYear; // 공용연수(NOTNULL)

    @Column(name="pi_type")
    private String piType; // 형식구분(NULL)

    @Column(name="pi_erection_cost")
    private Long piErectionCost; // 취득원가(NOTNULL)

    @Column(name="pi_safety_level")
    private String piSafetyLevel; // 안전등급(NOTNULL)

    @Column(name="pi_usability_and_goal_level")
    private String piUsabilityAndGoalLevel; // 사용성등급(NOTNULL) , 목표안전등급

    @Column(name="pi_maintenance_delay")
    private Double piMaintenanceDelay;  // 유지보수기간(NOTNULL)

    @Column(name="pi_management")
    private String piManagement; // 관리주체(NULL)

    @Column(name="pi_agency")
    private String piAgency; // 관리감독기관(NULL)

    @Column(name="pi_aadt")
    private Double piAADT; // 연평균일교통량(NOTNULL)

    @Column(name="pi_business")
    private String piBusiness; // 사업구분(NOTNULL)

    @Column(name="pi_business_type")
    private String piBusinessType; // 사업유형(NOTNULL)

    @Column(name="pi_target_absence")
    private String piTargetAbsence; // 대상부재(NULL)

    @Column(name="pi_business_classification")
    private String piBusinessClassification; // 사업분류(NOTNULL)

    @Column(name="pi_business_expenses")
    private Long piBusinessExpenses; // 사업비용(NOTNULL)

    @Column(name="pi_before_safety_rating")
    private String piBeforeSafetyRating; // 사업전 부재 안전등급(NOTNULL)

    @Column(name="pi_after_safety_rating")
    private String piAfterSafetyRating; // 사업후 부재 안전등급(NOTNULL)

    @Column(name="pi_business_validity")
    private String piBusinessValidity; // 사업추진 타당성( 0 : 해당사유 외, 1 : 법에 따른 의무사업, 2 : 법정계획/설계기준에 따른 의무사업, 3 : 자체계획/의결에 따른 사업

    @Column(name="pi_whether")
    private Double piWhether; // 최근 1년간 민원 및 사고발생 건수(NOTNULL)

    @Column(name="pi_rater_base_year")
    private Double piRaterBaseYear; // 평가 기준년도(NOTNULL)

    @Column(name="pi_rater")
    private String piRater; // 평가자이름(NULL)

    @Column(name="pi_rater_belong")
    private String piRaterBelong; // 평자가 소속(NULL)

    @Column(name="pi_rater_phone")
    private String piRaterPhone; // 평가자 연락처(NULL)

    @Column(name="pi_input_count")
    private Integer piInputCount; //대안카운트(NULL)

    @Column(name="pi_input_great")
    private Integer piInputGreat; //우수대안인지 0 or 1(NULL)

    @Column(name="pi_input_middle_save")
    private Integer piInputMiddleSave; //작성완료된 글인지 0 or 1(NULL)

    @Column(name="pi_input_skip")
    private String piInputSkip; // 스킵여부 0:적합, 1:부적합, 2: 스킵

    @Column(name="pi_file_yn")
    private String piFileYn; // 파일업로드여부 0: 웹등록, 1: 파일등록

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
