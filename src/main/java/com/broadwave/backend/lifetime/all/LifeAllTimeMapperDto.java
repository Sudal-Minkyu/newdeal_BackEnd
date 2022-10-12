package com.broadwave.backend.lifetime.all;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Time :
 * Remark : 뉴딜 생애주기 의사결정 지원서비스 전체부분 MapperDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LifeAllTimeMapperDto {

    private Long ltId; // 고유ID값(NOTNULL)

    private String ltBridgeName; // 교량명(NULL)
    private String ltAbsence; // 교량형식(NOTNULL)

    private String ltSpanNum; // 경간수(NOTNULL)
    private String ltAbsenceCode; // 부재코드(NOTNULL)

    private Double ltAllTeaRoad; // 차로수(NOTNULL)
    private String ltAllKind; // 종별구분(NOTNULL)
    private Double ltAllLength; // 연장(NOTNULL)
    private Double ltAllArea; // 폭(NOTNULL)
    private String ltAllCompletionDate; // 준공일자(NOTNULL)
    private String ltAllInputDate; // 평가기준 일자(NOTNULL)
    private Integer ltAllStage; // 평가단계 횟수

    private String ltAllRank; // 현재 상태등급(NOTNULL)

    private Double ltDamageBRank; // B등급 손상지수(NOTNULL)
    private Double ltDamageCRank; // C등급 손상지수(NOTNULL)
    private Double ltDamageDRank; // D등급 손상지수(NOTNULL)
    private Double ltDamageERank; // E등급 손상지수(NOTNULL)

    private Double ltDiscountRate; // 할인율(NOTNULL)
    private Double ltIncrease; // 열화증가율(NOTNULL)

    private Double ltPeriodicYear; // 정기점검 년수(NOTNULL)
    private Double ltPeriodicNum; // 정기점검 횟수(NOTNULL)
    private Double ltPeriodicCost; // 정기점검 바용(NOTNULL)

    private Double ltCloseYear; // 정밀점검 년수(NOTNULL)
    private Double ltCloseNum; // 정밀점검 횟수(NOTNULL)
    private Double ltCloseCost; // 정밀점검 비용(NOTNULL)

    private Double ltSafetyYear; // 정밀안전점검 년수(NOTNULL)
    private Double ltSafetyNum; // 정밀안전점검 횟수(NOTNULL)
    private Double ltSafetyCost; // 정밀안전점검 비용(NOTNULL)

    public Double getLtDiscountRate() {
        return ltDiscountRate/100;
    }

    public Double getLtIncrease() {
        return ltIncrease/100;
    }

}
