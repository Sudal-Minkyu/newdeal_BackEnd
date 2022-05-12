package com.broadwave.backend.account.AccountDtos;

/**
 * @author Minkyu
 * Date : 2022-05-12
 * Time :
 * Remark : 사용자 권한 구분
 */
public enum AccountRole {
    ROLE_USER("ROLE_USER", "사용자"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자");

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


