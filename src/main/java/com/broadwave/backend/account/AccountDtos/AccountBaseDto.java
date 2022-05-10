package com.broadwave.backend.account.AccountDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Time :
 * Remark : AccountRoleNameDto -> 2022-05-10 AccountBaseDto로 수정하여 팀소속명 필드추가
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBaseDto {

    private String username;
    private AccountRole role;
    private String teamname;

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role.getDesc();
    }

    public String getTeamname() {
        return teamname;
    }
}
