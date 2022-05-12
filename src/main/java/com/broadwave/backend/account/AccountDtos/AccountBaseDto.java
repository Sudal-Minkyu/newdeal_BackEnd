package com.broadwave.backend.account.AccountDtos;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Time :
 * Remark : AccountRoleNameDto -> 2022-05-10 AccountBaseDto로 수정하여 팀소속명,소속코드 필드추가
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBaseDto {

    private String username;
    private AccountRole role;
    private String teamcode;
    private String teamname;

}
