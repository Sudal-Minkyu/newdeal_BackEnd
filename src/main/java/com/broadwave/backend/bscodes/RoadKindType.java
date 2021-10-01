package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-06-11
 * Remark : 도로부속시설 도로의 종류
 */
public enum RoadKindType {
    RK01("RK01", "고속도로"),
    RK02("RK02", "국도"),
    RK03("RK03", "지방도"),
    RK04("RK04", "기타");

    private final String code;
    private final String desc;

    RoadKindType(String code, String desc) {
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
