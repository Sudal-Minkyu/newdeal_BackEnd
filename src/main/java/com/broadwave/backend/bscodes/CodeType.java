package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Remark : 코드 대분류코드
 */
public enum CodeType {
    C0001("C0001", "성능개선 사업평가 서비스"),
    C0002("C0002", "유지보수 우선순위 선정서비스");

    private final String code;
    private final String desc;

    CodeType(String code, String desc) {
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
