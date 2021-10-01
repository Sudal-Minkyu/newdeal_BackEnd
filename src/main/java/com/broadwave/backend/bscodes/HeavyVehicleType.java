package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-04-27
 * Remark : 중차량환경
 */
public enum HeavyVehicleType {
    HVT001("HVT001", "L"),
    HVT002("HVT002", "H");

    private String code;
    private String desc;

    HeavyVehicleType(String code, String desc) {
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