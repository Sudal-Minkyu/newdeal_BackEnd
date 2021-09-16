package com.broadwave.backend.lifetime.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Minkyu
 * Date : 2021-09-15
 * Time :
 * Remark : NewDeal LifeAllTime Service
*/
@Slf4j
@Service
public class LifeDetailTimeService {

    @Autowired
    LiftDetailTimeRepository liftDetailTimeRepository;

    @Autowired
    LifeDetailTimeRepositoryCustom lifeDetailTimeRepositoryCustom;

    public LifeDetailTime save(LifeDetailTime lifeDetailTime){
        return liftDetailTimeRepository.save(lifeDetailTime);
    }

    public LifeDetailTimeDto findById(Long id) {
        return lifeDetailTimeRepositoryCustom.findById(id);
    }
}
