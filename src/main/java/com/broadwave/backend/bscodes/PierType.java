package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2019-10-14
 * Remark :
 */
public enum PierType {
    PR001("PR001", "1등교"),
    PR002("PR002", "2등교"),
    PR003("PR003", "3등교"),
    PR004("PR004", "테스트");

    private String code;
    private String desc;

    PierType(String code, String desc) {
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

