package com.broadwave.backend.performance.excel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceExcelData {

    private String piFacilityType; // 시설유형
    private String piKind; // 종별구분
    private Integer piCompletionYear; // 준공연도
    private String piType; // 형식구분
    private Long piErectionCost; // 건설비용
    private String piSafetyLevel; // 안전등급
    private Integer piMaintenanceDelay; // 유지보수 지연기간
    private String piManagement; // 관리주체
    private String piAgency; // 관리감독기관
    private Long piAADT; // AADT
    private String piTruckRatio; // 트럭비율
    private String piBusiness; // 사업구분
    private String piBusinessType; // 사업유형
    private String piBusinessClassification; // 사업분류
    private String piBusinessInformation; // 사업내용
    private Long piBusinessExpenses; // 사업비용
    private String piBeforeSafetyRating; // 사업전 부재 안전등급
    private String piAfterSafetyRating; // 사업후 부재 안전등급
    private Integer piBusinessObligatory; // 법에 따른 의무사업
    private Integer piBusinessMandatory; // 법정계획에 따른 의무사업
    private Integer piBusinessPlanned; // 자체계획 사업
    private Integer piWhether; // 민원발생 여부
    private String piRater; // 평가자
    private String piRaterBelong; // 평가자 소속
    private String piRaterPhone; // 평가자 연락처

}