package com.broadwave.backend.env.salt;

import com.broadwave.backend.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-03-08
 * Time :
 * Remark : 대기중 염분량 추정 서비스 RestController
 */
@RestController
@Slf4j
@RequestMapping("/api/env/salt")
public class SaltRestController {

    @Autowired
    public SaltRestController() {

    }

    @PostMapping("seaAir")
    public ResponseEntity<Map<String,Object>> seaAir(@RequestParam(value="seaCheck", defaultValue="") String seaCheck,
                                                     @RequestParam(value="seaArea", defaultValue="") String seaArea,
                                                     @RequestParam (value="seaDistance", defaultValue="") double seaDistance,
                                                     @RequestParam (value="seaHeight", defaultValue="") double seaHeight){
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String[] seaAreaList = seaArea.split("_");
        double locationNum = 0;
        String seaNum = "11";

//        double result = Math.pow(5, 2); //5의제곱
//        System.out.println("5의 제곱은 : "+result);
//        Math.pow
        double dataResult; // 계산결과 값

        log.info("해역번호 : "+seaCheck);
        for(int i=0; i<seaAreaList.length; i++){
            if(i==0){
                locationNum = Double.parseDouble(seaAreaList[i]);
                log.info("지역계수 : "+locationNum);
            }else{
                seaNum = seaAreaList[i];
                log.info("지역검증 숫자 : "+seaNum);
            }
        }

        if(seaCheck.equals("1")){
//            log.info("동해안");
            if(seaNum.equals("11")){
                dataResult = locationNum*7.7911*Math.pow(seaDistance,-0.4458)*seaHeight;
            }else{
                dataResult = locationNum*10.929*Math.pow(seaDistance,-0.5193)*seaHeight;
            }
        }else if(seaCheck.equals("2")){
//            log.info("서해안");
            if(seaNum.equals("21")){
                dataResult = locationNum*6.9438*Math.pow(seaDistance,-0.4208)*seaHeight;
            }else{
                dataResult = locationNum*5.536*Math.pow(seaDistance,-0.3716)*seaHeight;
            }
        }else{
//            log.info("남해안");
            if(seaNum.equals("31")){
                dataResult = locationNum*1.784*Math.pow(seaDistance,-0.1257)*seaHeight;
            }else{
                dataResult = locationNum*10.686*Math.pow(seaDistance,-0.5144)*seaHeight;
            }
        }

//        log.info("dataResult : "+dataResult);
//        log.info("해안거리(m) : "+seaDistance);
//        log.info("높이(m) : "+seaHeight);

        data.put("dataResult",Math.round(dataResult*100)/100.0);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


}
