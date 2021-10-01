package com.broadwave.backend.bscodes.psc;

/**
 * @author Minkyu
 * Date : 2020-05-22
 * Remark : 손상구분
 */
public enum TunnelDivisionType {
    TDV01("TDV01", "외형손상"),
    TDV02("TDV02", "기능손상"),
    TDV03("TDV03", "설치부 구조적 손상");

    private final String code;
    private final String desc;

    TunnelDivisionType(String code, String desc) {
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
