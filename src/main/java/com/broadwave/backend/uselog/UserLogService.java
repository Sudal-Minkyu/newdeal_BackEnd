package com.broadwave.backend.uselog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Remark :
 */
@Slf4j
@Service
public class UserLogService {

    private final UserLogRepository userLogRepository;

    @Autowired
    public UserLogService(UserLogRepository userLogRepository) {
        this.userLogRepository = userLogRepository;
    }

    public UserLog save(UserLog userLog){
        return userLogRepository.save(userLog);
    }

}
