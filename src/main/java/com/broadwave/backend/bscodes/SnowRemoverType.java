package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-04-27
 * Remark : 제설제
 */
public enum SnowRemoverType {
    SRT001("SRT001", "A-"),
    SRT002("SRT002", "A+"),
    SRT003("SRT003", "B-"),
    SRT004("SRT004", "B+"),
    SRT005("SRT005", "C-"),
    SRT006("SRT006", "C+");

    private String code;
    private String desc;

    SnowRemoverType(String code, String desc) {
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