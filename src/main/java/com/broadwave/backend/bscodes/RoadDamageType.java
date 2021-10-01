package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-05-27
 * Remark : 도로부속시설 손상종류
 */
public enum RoadDamageType {
    RD01("RD01", "균열"),
    RD02("RD02", "콘크리트 염화물침투량"),
    RD03("RD03", "콘크리트 탄산화깊이"),
    RD04("RD04", "콘크리트 표면강도"),
    RD05("RD05", "철근노출");

    private final String code;
    private final String desc;

    RoadDamageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
