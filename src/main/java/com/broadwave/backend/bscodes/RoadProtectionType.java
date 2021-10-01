package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-06-25
 * Remark : 도로부속시설 방호등급
 */
public enum RoadProtectionType {
    RPT01("RPT01", "SB1"),
    RPT02("RPT02", "SB2"),
    RPT03("RPT03", "SB3"),
    RPT04("RPT04", "SB4"),
    RPT05("RPT05", "SB5"),
    RPT06("RPT06", "SB6"),
    RPT07("RPT07", "SB7");

    private final String code;
    private final String desc;

    RoadProtectionType(String code, String desc) {
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
