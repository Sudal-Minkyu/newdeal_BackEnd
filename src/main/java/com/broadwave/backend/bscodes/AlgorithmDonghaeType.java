package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-09-08
 * Remark : 알고리즘평가입력 전용 동해환경
 */
public enum AlgorithmDonghaeType {
    DT001("DT001", "A"),
    DT002("DT002", "B"),
    DT003("DT003", "C");

    private final String code;
    private final String desc;

    AlgorithmDonghaeType(String code, String desc) {
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