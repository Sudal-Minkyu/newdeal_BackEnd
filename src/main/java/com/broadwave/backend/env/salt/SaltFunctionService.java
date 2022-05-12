package com.broadwave.backend.env.salt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Minkyu
 * Date : 2022-05-12
 * Time :
 * Remark : 대기중 염분량 추정식 계산 서비스
 */
@Slf4j
@Service
public class SaltFunctionService {

    // 식(1) : y=7.794x^(-0.239)
    public double saltResult1(double height){
        log.info("식(1) 호출 -> y=7.794x^(-0.239)");
        return 7.794*Math.pow(height,-0.239);
    }

    // 식(2) : y=3.0532EXP(-0.005x)
    public double saltResult2(double height){
        log.info("식(1) 호출 -> y=3.0532EXP(-0.005x)");
        return 3.0532*Math.exp(-0.005*height);
    }

    // 식(3) : y=1.6928EXP(-0.011x)
    public double saltResult3(double height){
        log.info("식(1) 호출 -> y=1.6928EXP(-0.011x)");
        return 1.6928*Math.exp(-0.011*height);
    }

    // 식(4) : y=2.8456x^(-0.719)
    public double saltResult4(double height){
        log.info("식(1) 호출 -> y=2.8456x^(-0.719)");
        return 2.8456*Math.pow(height,-0.719);
    }

    // 식(5) : y=0.9189EXP(0.0168x)
    public double saltResult5(double height){
        log.info("식(1) 호출 -> y=0.9189EXP(0.0168x)");
        return 0.9189*Math.exp(0.0168*height);
    }

    // 식(6) : y=5.0952EXP(-0.018x)
    public double saltResult6(double height){
        log.info("식(1) 호출 -> y=5.0952EXP(-0.018x)");
        return 5.0952*Math.exp(-0.018*height);
    }

}