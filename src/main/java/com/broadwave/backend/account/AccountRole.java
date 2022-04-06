package com.broadwave.backend.account;

/**
 * @author InSeok
 * Date : 2019-03-25
 * Time : 09:33
 * Remark : 사용자 piPolicy 구분
 */
public enum AccountRole {
    ROLE_USER("ROLE_USER", "사용자"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_SAFETY("ROLE_SAFETY", "안전성요원");

    private final String code;
    private final String desc;

    AccountRole(String code, String desc) {
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


