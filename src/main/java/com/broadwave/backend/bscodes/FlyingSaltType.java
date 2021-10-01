package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-04-27
 * Remark : 비래염분
 */
public enum FlyingSaltType {
    FST001("FST001", "A"),
    FST002("FST002", "B"),
    FST003("FST003", "C");

    private String code;
    private String desc;

    FlyingSaltType(String code, String desc) {
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