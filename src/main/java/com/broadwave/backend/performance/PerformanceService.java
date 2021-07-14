package com.broadwave.backend.performance;

import com.broadwave.backend.keygenerate.KeyGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Minkyu
 * Date : 2021-07-09
 * Time :
 * Remark : NewDeal Performance Service
 *               노후화_기술성시트 , 노후화_정책성시트, 노후화_경제성시트, 노후화_종합평가표에 관련된 함수모음
*/
@Slf4j
@Service
public class PerformanceService {

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    KeyGenerateService keyGenerateService;

    public Performance save(Performance performance){
        log.info("성능 개선 사업 평가 서비스 엑셀파일 업로드 저장");
//        if ( performance.getPiAutoNum() == null || performance.getPiAutoNum().isEmpty()){
//            log.info("신규 PI코드생성");
//            Date now = new Date();
//            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
//            String autoNum = keyGenerateService.keyGenerate("nd_pi_input", yyMM.format(now), performance.getInsert_id());
//            performance.setPiAutoNum(autoNum);
//        }
        return performanceRepository.save(performance);
    }







    public void Test(){
        log.info("클래스 실행 테스트 입니다.");
    }








}
