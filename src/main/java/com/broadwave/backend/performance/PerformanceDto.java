package com.broadwave.backend.performance;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-07-07
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 Dto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class PerformanceDto {
    private String piFacilityType; // 시설유형(NOTNULL)\
    private String piFacilityName; // 시설유형(NULL)
    private String piKind; // 종별구분(NOTNULL)
    private Double piCompletionYear; // 준공연도(NOTNULL)
    private Double piPublicYear; // 공용연수(NOTNULL)
    private String piType; // 형식구분(NULL)
    private Long piErectionCost; // 취득원가(NOTNULL)
    private String piSafetyLevel; // 안전등급(NOTNULL)
    private String piGoalLevel; // 목표등급(NOTNULL)
    private Double piMaintenanceDelay;  // 유지보수기간(NOTNULL)
    private String piManagement; // 관리주체(NULL)
    private String piAgency; // 관리감독기관(NULL)
    private Double piAADT; // 연평균일교통량(NOTNULL)
    private String piBusiness; // 사업구분(NOTNULL)
    private String piBusinessType; // 사업유형(NOTNULL)
    private String piTargetAbsence; // 대상부재(NULL)
    private String piBusinessClassification; // 사업분류(NOTNULL)
    private String piBusinessInformation; // 사업내용(NULL)
    private Long piBusinessExpenses; // 사업비용(NOTNULL)
    private String piBeforeSafetyRating; // 사업전 부재 안전등급(NOTNULL)
    private String piAfterSafetyRating; // 사업후 부재 안전등급(NOTNULL)
    private Double piBusinessObligatory; // 법에 따른 의무사업(NOTNULL)
    private Double piBusinessMandatory; // 법정계획에 따른 의무사업(NOTNULL)
    private Double piBusinessPlanned; // 자체계획/의결에 따른 사업(NOTNULL)
    private Double piWhether; // 최근 1년간 민원 및 사고발생 건수(NOTNULL)
    private Double piRaterBaseYear; // 평가 기준년도(NOTNULL)
    private String piRater; // 평가자이름(NULL)
    private String piRaterBelong; // 평자가 소속(NULL)
    private String piRaterPhone; // 평가자 연락처(NULL)
    private Integer piInputCount; //대안카운트(NULL)

    public String getPiFacilityType() {
        return piFacilityType;
    }

    public String getPiFacilityName() {
        return piFacilityName;
    }

    public String getPiKind() {
        return piKind;
    }

    public Double getPiCompletionYear() {
        return piCompletionYear;
    }

    public Double getPiPublicYear() {
        return piPublicYear;
    }

    public String getPiType() {
        return piType;
    }

    public Long getPiErectionCost() {
        return piErectionCost;
    }

    public String getPiSafetyLevel() {
        return piSafetyLevel;
    }

    public String getPiGoalLevel() {
        return piGoalLevel;
    }

    public Double getPiMaintenanceDelay() {
        return piMaintenanceDelay;
    }

    public String getPiManagement() {
        return piManagement;
    }

    public String getPiAgency() {
        return piAgency;
    }

    public Double getPiAADT() {
        return piAADT;
    }

    public String getPiBusiness() {
        return piBusiness;
    }

    public String getPiBusinessType() {
        return piBusinessType;
    }

    public String getPiTargetAbsence() {
        return piTargetAbsence;
    }

    public String getPiBusinessClassification() {
        return piBusinessClassification;
    }

    public String getPiBusinessInformation() {
        return piBusinessInformation;
    }

    public Long getPiBusinessExpenses() {
        return piBusinessExpenses;
    }

    public String getPiBeforeSafetyRating() {
        return piBeforeSafetyRating;
    }

    public String getPiAfterSafetyRating() {
        return piAfterSafetyRating;
    }

    public Double getPiBusinessObligatory() {
        return piBusinessObligatory;
    }

    public Double getPiBusinessMandatory() {
        return piBusinessMandatory;
    }

    public Double getPiBusinessPlanned() {
        return piBusinessPlanned;
    }

    public Double getPiWhether() {
        return piWhether;
    }

    public Double getPiRaterBaseYear() {
        return piRaterBaseYear;
    }

    public String getPiRater() {
        return piRater;
    }

    public String getPiRaterBelong() {
        return piRaterBelong;
    }

    public String getPiRaterPhone() {
        return piRaterPhone;
    }

    public Integer getPiInputCount() {
        return piInputCount;
    }
}
