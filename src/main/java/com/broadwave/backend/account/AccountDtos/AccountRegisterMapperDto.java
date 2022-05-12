package com.broadwave.backend.account.AccountDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2021-09-02
 * Time :
 * Remark : 회원가입 MapperDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegisterMapperDto {
    private String userid;
    private String password;
    private String check_password;
    private String username;
    private String email;
    private String team;
}
