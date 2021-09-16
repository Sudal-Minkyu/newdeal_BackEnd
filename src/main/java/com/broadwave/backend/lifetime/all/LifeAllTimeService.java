package com.broadwave.backend.lifetime.all;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Time :
 * Remark : NewDeal LifeAllTime Service
*/
@Slf4j
@Service
public class LifeAllTimeService {

    @Autowired
    LiftAllTimeRepository liftAllTimeRepository;

    @Autowired
    LifeAllTimeRepositoryCustom lifeAllTimeRepositoryCustom;

    public LifeAllTime save(LifeAllTime lifeAllTime){
        return liftAllTimeRepository.save(lifeAllTime);
    }

    public LifeAllTimeDto findById(Long id) {
        return lifeAllTimeRepositoryCustom.findById(id);
    }
}
