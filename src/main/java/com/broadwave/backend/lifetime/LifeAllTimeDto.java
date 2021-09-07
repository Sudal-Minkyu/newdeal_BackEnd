package com.broadwave.backend.lifetime;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 전체부분 LifeAllTimeDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Slf4j
public class LifeAllTimeDto {

    private String ltBridgeCode; // 교량코드(NULL)
    private String ltBridgeName; // 교량명(NULL)
    private String ltSpanNum; // 경간수(NOTNULL)
    private String ltAbsenceCode; // 부재코드(NOTNULL)

    private Double ltAllTeaRoad; // 차로수(NOTNULL)
    private String ltAllKind; // 종별구분(NOTNULL)
    private Double ltAllLength; // 연장(NOTNULL)
    private Double ltAllArea; // 폭(NOTNULL)
    private String ltAllCompletionDate; // 준공일자(NOTNULL)
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
}
