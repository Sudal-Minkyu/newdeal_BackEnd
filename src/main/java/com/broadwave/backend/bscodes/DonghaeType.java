package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-04-27
 * Remark : 동해환경
 */
public enum DonghaeType {
    DT001("DT001", "A-"),
    DT002("DT002", "A+"),
    DT003("DT003", "B-"),
    DT004("DT004", "B+"),
    DT005("DT005", "C-"),
    DT006("DT006", "C+");

    private String code;
    private String desc;

    DonghaeType(String code, String desc) {
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