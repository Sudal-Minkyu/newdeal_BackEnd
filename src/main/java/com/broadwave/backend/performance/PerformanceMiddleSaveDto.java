package com.broadwave.backend.performance;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-07-20
 * Time :
 * Remark : 뉴딜 성능개선사업평가 중간저장 테이블
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class PerformanceMiddleSaveDto {

    private String piBusiness; // 사업구분(NOTNULL)
    private String piFacilityType; // 시설유형(NOTNULL)
    private String piFacilityName; // 시설유형(NULL)
    private String piKind; // 종별구분(NOTNULL)
    private Double piCompletionYear; // 준공연도(NOTNULL)
    private Double piPublicYear; // 공용연수(NOTNULL)
    private String piType; // 형식구분(NULL)
    private Long piErectionCost; // 취득원가(NOTNULL)
    private String piSafetyLevel; // 안전등급(NOTNULL)
    private String piUsabilityAndGoalLevel; // 사용성등급(NOTNULL) , 목표안전등급
    private Double piMaintenanceDelay;  // 유지보수기간(NOTNULL)
    private String piManagement; // 관리주체(NULL)
    private String piAgency; // 관리감독기관(NULL)
    private Double piAADT; // 연평균일교통량(NOTNULL)

    private Double piRaterBaseYear; // 평가 기준년도(NOTNULL)
    private String piRater; // 평가자이름(NULL)
    private String piRaterBelong; // 평자가 소속(NULL)
    private String piRaterPhone; // 평가자 연락처(NULL)

}
