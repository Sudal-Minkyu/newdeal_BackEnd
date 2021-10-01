package com.broadwave.backend.bscodes.psc;

/**
 * @author Minkyu
 * Date : 2019-10-24
 * Remark :
 */
public enum RescueUpDown {
    상부("상부", "상부구조"),
    하부("하부", "하부구조");

    private final String code;
    private final String desc;

    RescueUpDown(String code, String desc) {
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