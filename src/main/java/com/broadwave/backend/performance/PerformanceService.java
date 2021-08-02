package com.broadwave.backend.performance;

import com.broadwave.backend.keygenerate.KeyGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-07-09
 * Time :
 * Remark : NewDeal Performance Service
*/
@Slf4j
@Service
public class PerformanceService {

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    PerformanceRepositoryCustom performanceRepositoryCustom;

    @Autowired
    KeyGenerateService keyGenerateService;

    public void save(Performance performance){
        performanceRepository.save(performance);
    }

    public List<PerformanceDto> findByAutoNum(String autoNum) {
        return performanceRepositoryCustom.findByAutoNum(autoNum);
    }

    public PerformanceCheckDto findByInsertId(String insert_id) {
        return performanceRepositoryCustom.findByInsertId(insert_id);
    }

    public  PerformanceMiddleDataDto findByInsertIAndAutoNum(String insert_id, String autoNum) {
        return performanceRepositoryCustom.findByInsertIAndAutoNum(insert_id,autoNum);
    }

    public Optional<Performance> findByPiAutoNumAndInsert_id(String autoNum, String insert_id) {
        return performanceRepository.findByPiAutoNumAndInsert_id(autoNum,insert_id);
    }

    public Performance findByBusiness(String autoNum, String insert_id) {
        return performanceRepositoryCustom.findByBusiness(autoNum,insert_id);
    }

    public List<Performance> findByPiAutoNumAndInsert_idDel(String autoNum, String insert_id) {
//        return performanceRepository.findByPiAutoNumAndInsert_idDel(autoNum,insert_id);
        return performanceRepositoryCustom.findByPiAutoNumAndInsert_idDel(autoNum,insert_id);
    }

    public Optional<Performance> findByPiAutoNumAndInsert_idAndPiInputCount(String autoNum, String insert_id, Integer piInputCount) {
        return performanceRepository.findByPiAutoNumAndInsert_idAndPiInputCount(autoNum,insert_id,piInputCount);
    }

    public void delete(Performance performance) {
        performanceRepository.delete(performance);
    }

    public List<PerformanceMiddleBusinessDataDto> findByInsertIAndAutoNum2(String insert_id, String autoNum) {
        return performanceRepositoryCustom.findByInsertIAndAutoNum2(insert_id,autoNum);
    }

    public Optional<Performance> findById(Long id) {
        return performanceRepository.findById(id);
    }

    public PerformancePiBusinessDto findByInsertIAndAutoNumAndCount(String insert_id, String autoNum, int count) {
        return performanceRepositoryCustom.findByInsertIAndAutoNumAndCount(insert_id,autoNum,count);
    }
}
