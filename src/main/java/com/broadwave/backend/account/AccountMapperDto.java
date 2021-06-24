package com.broadwave.backend.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author InSeok
 * Date : 2019-03-25
 * Time : 10:11
 * Remark : Account Dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountMapperDto {

    private String userid;
    private String username;
    private String password;
    private String email;
    private AccountRole role;
    private String teamcode;
    private String mode;

}
