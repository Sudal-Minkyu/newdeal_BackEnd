package com.broadwave.backend.uselog;

import com.broadwave.backend.account.AccountDtoWithTeam;
import com.broadwave.backend.account.AccountRoleNameDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author InSeok
 * Date : 2019-03-29
 * Time : 10:22
 * Remark :
 */
public interface UserLogRepositoryCustom {
    Long findBySearchCount();
}
