package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-09-08
 * Remark : 알고리즘평가입력 전용 제설제
 */
public enum AlgorithmSnowRemoverType {
    SRT001("SRT001", "A"),
    SRT002("SRT002", "B"),
    SRT003("SRT003", "C");

    private final String code;
    private final String desc;

    AlgorithmSnowRemoverType(String code, String desc) {
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