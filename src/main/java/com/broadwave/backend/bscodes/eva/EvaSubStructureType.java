package com.broadwave.backend.bscodes.eva;

/**
 * @author InSeok
 * Date : 2019-09-06
 * Remark : 알고리즘평가입력 -> 하부구조타입
 */
public enum EvaSubStructureType {
    SS001("SS001", "T형 교각식"),
    SS002("SS002", "V형 교각식"),
    SS003("SS003", "구주식"),
    SS004("SS004", "구주식교대"),
    SS005("SS005", "라멘식"),
    SS006("SS006", "라멘식교대"),
    SS007("SS007", "목조식"),
    SS008("SS008", "박스식교대"),
    SS009("SS009", "반중력식"),
    SS010("SS010", "반중력식교대"),
    SS011("SS011", "벽식"),
    SS012("SS012", "부벽식교대"),
    SS013("SS013", "아치식"),
    SS014("SS014", "역T형식교대"),
    SS015("SS015", "중력식"),
    SS016("SS016", "중력식교대"),
    SS017("SS017", "ㅠ형식"),
    SS018("SS018", "기타")
    ;

    private String code;
    private String desc;


    EvaSubStructureType(String code, String desc) {
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
