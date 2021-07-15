package com.broadwave.backend.performance;

import com.broadwave.backend.keygenerate.KeyGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        log.info("성능 개선 사업 평가 서비스 엑셀파일 업로드 저장");
        performanceRepository.save(performance);
    }

    public List<PerformanceDto> findByAutoNum(String autoNum) {
        return performanceRepositoryCustom.findByAutoNum(autoNum);
    }

}
