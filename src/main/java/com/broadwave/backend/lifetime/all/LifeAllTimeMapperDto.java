package com.broadwave.backend.lifetime.all;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

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

    private String ltBridgeName; // 교량명(NULL)
    private String ltSpanNum; // 경간수(NOTNULL)
    private String ltAbsenceCode; // 부재코드(NOTNULL)

    private Double ltAllTeaRoad; // 차로수(NOTNULL)
    private String ltAllKind; // 종별구분(NOTNULL)
    private Double ltAllLength; // 연장(NOTNULL)
    private Double ltAllArea; // 폭(NOTNULL)
    private String ltAllCompletionDate; // 준공일자(NOTNULL)
    private String ltAllInputDate; // 평가기준 일자(NOTNULL)
    private Integer ltAllStage; // 평가단계 횟수

    private Double ltAllVolume; // 전체물량(NOTNULL)

    private Double ltDamageBRank; // B등급 손상지수(NOTNULL)
    private Double ltDamageCRank; // C등급 손상지수(NOTNULL)
    private Double ltDamageDRank; // D등급 손상지수(NOTNULL)
    private Double ltDamageERank; // E등급 손상지수(NOTNULL)

    private Double ltDiscountRate; // 할인율(NOTNULL)
    private Double ltIncrease; // 열화증가율(NOTNULL)

    private Double ltPeriodicFrequency; // 정기점검 빈도수(NOTNULL)
    private Double ltPeriodicCost; // 정기점검 바용(NOTNULL)
    private Double ltCloseFrequency; // 정밀점검 빈도수(NOTNULL)
    private Double ltCloseCost; // 정밀점검 비용(NOTNULL)
    private Double ltSafetyFrequency; // 정밀안전점검 빈도수(NOTNULL)
    private Double ltSafetyCost; // 정밀안전점검 비용(NOTNULL)


    public Double getLtDiscountRate() {
        return ltDiscountRate/100;
    }

    public Double getLtIncrease() {
        return ltIncrease/100;
    }

}
