package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-05-22
 * Remark : 터널 열화종류
 */
public enum TunnelFireType {
    FT01("FT01", "콘크리트 염화물침투량"),
    FT02("FT02", "콘크리트 탄산화깊이"),
    FT03("FT03", "콘크리트 표면강도"),
    FT04("FT04", "누수 및 백태");

    private final String code;
    private final String desc;

    TunnelFireType(String code, String desc) {
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
