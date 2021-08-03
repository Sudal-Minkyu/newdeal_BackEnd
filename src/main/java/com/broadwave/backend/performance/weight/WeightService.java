package com.broadwave.backend.performance.weight;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Time :
 * Remark : NewDeal Weight Service
*/
@Slf4j
@Service
public class WeightService {

    @Autowired
    WeightRepository weightRepository;

    @Autowired
    WeigthRepositoryCustom weigthRepositoryCustom;

    public void save(Weight weight){
        log.info("성능 개선 사업 평가 서비스 가중치 저장");
        weightRepository.save(weight);
    }

    public WeightDto findByAutoNum(String autoNum){
        return weigthRepositoryCustom.findByAutoNum(autoNum);
    }

    public void delete(Weight weight){
        weightRepository.delete(weight);
    }

    public Optional<Weight> findByAutoNumAndInsertId(String autoNum, String insert_id) {
        return weightRepository.findByAutoNumAndInsertId(autoNum,insert_id);
    }

}
