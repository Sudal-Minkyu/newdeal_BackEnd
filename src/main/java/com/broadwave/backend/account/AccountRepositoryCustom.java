package com.broadwave.backend.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author InSeok
 * Date : 2019-03-29
 * Time : 10:22
 * Remark :
 */
public interface AccountRepositoryCustom {
    Page<AccountDtoWithTeam> findAllBySearchStrings(String userid, String username,String teamname, Pageable pageable);

    AccountRoleNameDto findByRoleAndName(String userid);

    Long findByAccountCount();
}
