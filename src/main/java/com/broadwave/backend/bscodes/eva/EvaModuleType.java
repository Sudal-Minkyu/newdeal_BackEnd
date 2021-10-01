package com.broadwave.backend.bscodes.eva;

/**
 * @author InSeok
 * Date : 2019-09-06
 * Remark : 알고리즘평가입력 -> 사용모듈타입
 */
public enum EvaModuleType {

    EM001("EM001", "1:전체 거동특성 미포함"),
    EM002("EM002", "2:전체 거동특성 포함")
    ;

    private String code;
    private String desc;


    EvaModuleType(String code, String desc) {
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
