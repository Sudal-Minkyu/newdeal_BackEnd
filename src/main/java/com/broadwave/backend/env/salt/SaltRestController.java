package com.broadwave.backend.env.salt;

import com.broadwave.backend.account.AccountDtos.AccountBaseDto;
import com.broadwave.backend.account.AccountService;
import com.broadwave.backend.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    private final SaltFunctionService saltFunctionService;
    private final AccountService accountService;

    @Autowired
    public SaltRestController(SaltFunctionService saltFunctionService, AccountService accountService) {
        this.saltFunctionService = saltFunctionService;
        this.accountService = accountService;
    }

    // 열화환경 관리자인지 확인하는 함수
    @GetMapping("seaAdminCheck")
    public ResponseEntity<Map<String,Object>> seaAdminCheck(HttpServletRequest request) {

        log.info("seaAdminCheck 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        AccountBaseDto accountBaseDto = accountService.findByAcountBase(insert_id);
        log.info("accountBaseDto : "+accountBaseDto);
        if(accountBaseDto.getTeamcode().equals("T00004") || accountBaseDto.getTeamcode().equals("T00001")){
            data.put("result","1");
        }else{
            data.put("result","0");
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // 해안가 대기중 비래염분 계산하기 함수
    @PostMapping("seaAir")
    public ResponseEntity<Map<String,Object>> seaAir(@RequestParam(value="seaCheck", defaultValue="") String seaCheck,
                                                     @RequestParam(value="seaArea", defaultValue="") String seaArea,
                                                     @RequestParam (value="seaDistance", defaultValue="") double seaDistance,
                                                     @RequestParam (value="seaHeight", defaultValue="") double seaHeight){
        log.info("seaAir 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
//        log.info("해안지역 1:동,2:남,3:서 seaCheck"+seaCheck);
//        log.info("seaArea : "+seaArea);
//        log.info("해안거리 seaDistance : "+seaDistance);
//        log.info("높이 seaHeight : "+seaHeight);
        String[] seaAreaList = seaArea.split("_");
//        log.info("seaAreaList : "+seaAreaList);
        double locationNum = 0;
        String seaNum = "11";
//        double result = Math.pow(5, 2); //5의제곱
//        System.out.println("5의 제곱은 : "+result);
//        Math.pow
        double dataResult; // 계산결과 값
//        log.info("해역번호 : "+seaCheck);
        for(int i=0; i<seaAreaList.length; i++){
            if(i==0){
                locationNum = Double.parseDouble(seaAreaList[i]);
                log.info("지역계수 locationNum : "+locationNum);
            }else{
                seaNum = seaAreaList[i];
                log.info("지역검증 숫자 seaNum : "+seaNum);
            }
        }
        double heightNum; // 높이계수
        if(0<= seaHeight && seaHeight < 2){
            heightNum = 2;
        }else if(2<= seaHeight && seaHeight <= 30){
            if(500 < seaDistance){
                // 공식 : y=-0.0009x2+0.0259x+0.9722
                heightNum = (-0.0009 * Math.pow(seaHeight, 2)) + (0.0259*seaHeight) + 0.9722;
            }else if(400 < seaDistance && seaDistance <= 500){
                if(2<= seaHeight && seaHeight <= 10){
                    // 공식 : y=-0.0062x2+0.0765x+0.8717
                    heightNum = (-0.0062 * Math.pow(seaHeight, 2)) + (0.0765*seaHeight) + 0.8717;
                }else{
                    // 공식 : y=-0.0012x2+0.0375x+0.76
                    heightNum = (-0.0012 * Math.pow(seaHeight, 2)) + (0.0375*seaHeight) + 0.76;
                }
            }else if(300 < seaDistance && seaDistance <= 400){
                // 공식 : y=-0.0009x2+0.0187x+0.9888
                heightNum = (-0.0009 * Math.pow(seaHeight, 2)) + (0.0187*seaHeight) + 0.9888;
            }else if(200 < seaDistance && seaDistance <= 300){
                if(2<= seaHeight && seaHeight <= 10){
                    // 공식 : y=-0.0058x2+0.0803x+0.8625
                    heightNum = (-0.0058 * Math.pow(seaHeight, 2)) + (0.0803*seaHeight) + 0.8625;
                }else{
                    // 공식 : y=-0.0036x2+0.124x+0.21
                    heightNum = (-0.0036 * Math.pow(seaHeight, 2)) + (0.124*seaHeight) + 0.21;
                }
            }else if(100 < seaDistance && seaDistance <= 200){
                if(2<= seaHeight && seaHeight <= 5){
                    // 공식 : y=0.05x+0.9
                    heightNum = (0.05*seaHeight) + 0.9;
                }else{
                    // 공식 : y=-0.0002x2-0.015x+1.2401
                    heightNum = (-0.0002 * Math.pow(seaHeight, 2)) - (0.015*seaHeight) + 1.2401;
                }
            }else if(30 < seaDistance && seaDistance <= 100){
                // 공식 : y=-0.269ln(x)+1.1912
//                log.info("공식 : y=-0.269ln(x)+1.1912");
                heightNum = (-0.269 * Math.log(seaHeight)) + 1.1912;
            }else{ // 0 < seaDistance && seaDistance <= 30){
                // 공식 : y=-0.286ln(x)+1.2008
//                log.info("공식 : y=-0.286ln(x)+1.2008");
                heightNum = (-0.286 * Math.log(seaHeight)) + 1.2008;
            }
        }else{
            heightNum = 30;
        }

        log.info("높이계수 heightNum : "+heightNum);

        if(seaCheck.equals("1")){
//            log.info("동해안");
            if(seaNum.equals("11")){
                dataResult = locationNum*7.7911*Math.pow(seaDistance,-0.4458)*heightNum;
            }else{
                dataResult = locationNum*10.929*Math.pow(seaDistance,-0.5193)*heightNum;
            }
        }else if(seaCheck.equals("2")){
//            log.info("서해안");
            if(seaNum.equals("21")){
                dataResult = locationNum*6.9438*Math.pow(seaDistance,-0.4208)*heightNum;
            }else{
                dataResult = locationNum*5.536*Math.pow(seaDistance,-0.3716)*heightNum;
            }
        }else{
//            log.info("남해안");
            if(seaNum.equals("31")){
                dataResult = locationNum*1.784*Math.pow(seaDistance,-0.1257)*heightNum;
            }else{
                dataResult = locationNum*10.686*Math.pow(seaDistance,-0.5144)*heightNum;
            }
        }

        log.info("dataResult : "+dataResult);
        dataResult = Math.round(dataResult*10)/10.0;

        log.info("소수점처리 : "+dataResult);
        log.info("해안거리(m) : "+seaDistance);
        log.info("높이(m) : "+seaHeight);

        data.put("dataResult",dataResult);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // 해상교량 대기중 비래염분 지역계산 함수
    @PostMapping("locationSea")
    public ResponseEntity<Map<String,Object>> locationSea(@RequestParam(value="locationSeaCheck", defaultValue="") String locationSeaCheck,
                                                          @RequestParam(value="locationSeaArea", defaultValue="") String locationSeaArea,
                                                          @RequestParam (value="locationCalculationHeight1", defaultValue="") double locationCalculationHeight1,
                                                          @RequestParam (value="locationCalculationHeight2", defaultValue="") double locationCalculationHeight2,
                                                          @RequestParam (value="locationHeight", defaultValue="") double locationHeight){
        log.info("locationSea 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        double dataResult; // 계산결과 값

        log.info("추정식1 높이 : "+locationCalculationHeight1);
        log.info("추정식2 높이 : "+locationCalculationHeight2);
        log.info("해역 : "+locationSeaCheck);
        log.info("지역 : "+locationSeaArea);
        log.info("해수면으로부터 높이(m) : "+locationHeight);

        if(locationSeaCheck.equals("1")){
            if(locationSeaArea.equals("1")){
                dataResult = saltFunctionService.saltResult2(locationHeight);
            }else{
                dataResult = saltFunctionService.saltResult1(locationHeight);
            }
        }else if(locationSeaCheck.equals("2")){
            if(locationSeaArea.equals("1")){
                if(0<= locationHeight && locationHeight< locationCalculationHeight1){
                    dataResult = saltFunctionService.saltResult4(3);
                }else if(locationCalculationHeight1<= locationHeight && locationHeight<= locationCalculationHeight2){
                    dataResult = saltFunctionService.saltResult4(locationHeight);
                }else {
                    dataResult = saltFunctionService.saltResult4(5);
                }
            }else if(locationSeaArea.equals("2")){
                dataResult = saltFunctionService.saltResult2(locationHeight);
            }else{
                dataResult = saltFunctionService.saltResult1(locationHeight);
            }
        }else{
            if(locationSeaArea.equals("1")){
                if(0<= locationHeight && locationHeight< locationCalculationHeight1){
                    dataResult = saltFunctionService.saltResult4(3);
                }else if(locationCalculationHeight1<= locationHeight && locationHeight<= locationCalculationHeight2){
                    dataResult = saltFunctionService.saltResult4(locationHeight);
                }else {
                    dataResult = saltFunctionService.saltResult4(5);
                }
            }else if(locationSeaArea.equals("2")){
                dataResult = saltFunctionService.saltResult3(locationHeight);
            }else{
                dataResult = saltFunctionService.saltResult2(locationHeight);
            }
        }

        dataResult = Math.round(dataResult*10)/10.0;
        log.info("결과값 : "+dataResult);
        data.put("dataResult",dataResult);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // 해상교량 대기중 비래염분 교량계산 함수
    @PostMapping("bridgeSea")
    public ResponseEntity<Map<String,Object>> bridgeSea(@RequestParam(value="bridgeSeaCheck", defaultValue="") String bridgeSeaCheck,
                                                        @RequestParam(value="bridgeSeaArea", defaultValue="") String bridgeSeaArea,
                                                        @RequestParam(value="bridgeHeight", defaultValue="") double bridgeHeight){

        log.info("bridgeSea 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info("해역 : "+bridgeSeaCheck);
        log.info("교량 : "+bridgeSeaArea);
        log.info("해수면으로부터 높이(m) : "+bridgeHeight);

        double dataResult; // 계산결과 값

        if(bridgeSeaCheck.equals("1")){
            if(bridgeSeaArea.equals("1")){
                dataResult = saltFunctionService.saltResult3(bridgeHeight);
            }else if(bridgeSeaArea.equals("2")){
                if(0<= bridgeHeight && bridgeHeight< 3){
                    dataResult = saltFunctionService.saltResult4(3);
                }else if(3<= bridgeHeight && bridgeHeight<= 5){
                    dataResult = saltFunctionService.saltResult4(bridgeHeight);
                }else {
                    dataResult = saltFunctionService.saltResult4(5);
                }
            }else{
                dataResult = saltFunctionService.saltResult1(bridgeHeight);
            }
        }else{
            if(bridgeSeaArea.equals("1")){
                dataResult = saltFunctionService.saltResult1(bridgeHeight);
            }else if(bridgeSeaArea.equals("2")){
                dataResult = saltFunctionService.saltResult2(bridgeHeight);
            }else{
                if(0<= bridgeHeight && bridgeHeight<= 50){
                    dataResult = saltFunctionService.saltResult5(bridgeHeight);
                }else if(50< bridgeHeight && bridgeHeight<= 150){
                    dataResult = saltFunctionService.saltResult6(bridgeHeight);
                }else {
                    dataResult = saltFunctionService.saltResult6(150);
                }
            }
        }

        dataResult = Math.round(dataResult*10)/10.0;
        log.info("결과값 : "+dataResult);
        data.put("dataResult",dataResult);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }



}
