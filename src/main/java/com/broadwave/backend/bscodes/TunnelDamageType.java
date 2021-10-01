package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-05-22
 * Remark : 터널 손상종류
 */
public enum TunnelDamageType {
    TD01("TD01", "균열"),
    TD02("TD02", "박리/박락"),
    TD03("TD03", "파손"),
    TD04("TD04", "손상");

    private final String code;
    private final String desc;

    TunnelDamageType(String code, String desc) {
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
