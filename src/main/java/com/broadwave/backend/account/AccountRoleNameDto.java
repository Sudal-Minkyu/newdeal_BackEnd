package com.broadwave.backend.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Time :
 * Remark : AccountRoleNameDto
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRoleNameDto {

    private String username;
    private AccountRole role;

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role.getDesc();
    }
}
