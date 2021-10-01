package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2019-10-29
 * Remark : 비래염분(해안구분)Enum
 */
public enum EvSalt {
    EVS01("EVS01", "1: 동해안"),
    EVS02("EVS02", "2: 서해안"),
    EVS03("EVS03", "3: 남해안"),
    EVS04("EVS04", "4: 내륙");

    private String code;
    private String desc;

    EvSalt(String code, String desc) {
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
