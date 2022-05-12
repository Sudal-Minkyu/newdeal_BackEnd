package com.broadwave.backend.uselog;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Remark :
 */
public interface UserLogRepository extends JpaRepository<UserLog,Long>, UserLogRepositoryCustom {

}
