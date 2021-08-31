package com.broadwave.backend.lifetime;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Time :
 * Remark : 뉴딜 생애주기 의사결정 지원서비스 전체부분 MapperDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Slf4j
public class LifeAllTimeMapperDto {

//    private String ltBridgeCode; // 교량코드(NULL)
    private String ltBridgeName; // 교량명(NULL)
    private String ltSpanNum; // 경간수(NOTNULL)
    private String ltAbsenceCode; // 부재코드(NOTNULL)

    private Double ltDamageBRank; // B등급 손상지수(NOTNULL)
    private Double ltDamageCRank; // C등급 손상지수(NOTNULL)
    private Double ltDamageDRank; // D등급 손상지수(NOTNULL)
    private Double ltDamageERank; // E등급 손상지수(NOTNULL)

    private Double ltAllVolume; // 전체물량(NOTNULL)
    private Double ltDiscountRate; // 할인율(NOTNULL)
    private Double ltIncrease; // 열화증가율(NOTNULL)

    private Double ltPeriodicFrequency; // 정기점검 빈도수(NOTNULL)
    private Double ltPeriodicCost; // 정기점검 바용(NOTNULL)
    private Double ltCloseFrequency; // 정밀점검 빈도수(NOTNULL)
    private Double ltCloseCost; // 정밀점검 비용(NOTNULL)
    private Double ltSafetyFrequency; // 정밀안전점검 빈도수(NOTNULL)
    private Double ltSafetyCost; // 정밀안전점검 비용(NOTNULL)

    public static Logger getLog() {
        return log;
    }

//    public String getLtBridgeCode() {
//        return ltBridgeCode;
//    }

    public String getLtBridgeName() {
        return ltBridgeName;
    }

    public String getLtSpanNum() {
        return ltSpanNum;
    }

    public String getLtAbsenceCode() {
        return ltAbsenceCode;
    }

    public Double getLtDamageBRank() {
        return ltDamageBRank;
    }

    public Double getLtDamageCRank() {
        return ltDamageCRank;
    }

    public Double getLtDamageDRank() {
        return ltDamageDRank;
    }

    public Double getLtDamageERank() {
        return ltDamageERank;
    }

    public Double getLtAllVolume() {
        return ltAllVolume;
    }

    public Double getLtDiscountRate() {
        return ltDiscountRate/100;
    }

    public Double getLtIncrease() {
        return ltIncrease/100;
    }

    public Double getLtPeriodicFrequency() {
        return ltPeriodicFrequency;
    }

    public Double getLtPeriodicCost() {
        return ltPeriodicCost;
    }

    public Double getLtCloseFrequency() {
        return ltCloseFrequency;
    }

    public Double getLtCloseCost() {
        return ltCloseCost;
    }

    public Double getLtSafetyFrequency() {
        return ltSafetyFrequency;
    }

    public Double getLtSafetyCost() {
        return ltSafetyCost;
    }
}
