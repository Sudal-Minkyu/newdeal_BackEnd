package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-05-21
 * Remark : 터널 재료실험종류
 */
public enum MaterialExperimentType {

    MET01("MET01", "콘크리트강도"),
    MET02("MET02", "철근피복두께"),
    MET03("MET03", "철근부식도"),
    MET04("MET04", "콘크리트염화물량"),
    MET05("MET05", "콘크리트탄산화깊이");

    private final String code;
    private final String desc;

    MaterialExperimentType(String code, String desc) {
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
