package com.broadwave.backend.predict;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-07-25
 * Remark : NEWDEAL 인공지능기반 미래예측 컨트롤러 (권태호박사)
 */
@Slf4j
@RestController
@RequestMapping("/api/predict")
public class PredictRestController {

    private final PredictService predictService;

    @Autowired
    public PredictRestController(PredictService predictService) {

        this.predictService = predictService;
    }

    // NEWDEAL 센서리스트 호출
    @GetMapping("/sensorList")
    public ResponseEntity<Map<String,Object>> sensorList() throws JSONException {
        return predictService.sensorList();
    }

    // NEWDEAL 교량리스트 호출
    @GetMapping("/bridge")
    public ResponseEntity<Map<String,Object>> bridge() throws JSONException {
        return predictService.bridge();
    }

    // NEWDEAL 교량에 설치된 센서리스트 호출
    @GetMapping("/bridgeSensor")
    public ResponseEntity<Map<String,Object>> bridgeSensor(@RequestParam(value="bridgeId", defaultValue="")String bridgeId) throws JSONException {
        return predictService.bridgeSensor(bridgeId);
    }

    // NEWDEAL 센서 전체데이터 호출
    @GetMapping("/sensorAllData")
    public ResponseEntity<Map<String,Object>> sensorAllData(@RequestParam(value="sensor", defaultValue="")String sensor,
                                                            @RequestParam(value="channelNumber", defaultValue="")String channelNumber) throws JSONException {
        return predictService.sensorAllData(sensor, channelNumber);
    }

    // NEWDEAL 센서 미래예측 데이터 호출
    @GetMapping("/futureSensorDataGet")
    public ResponseEntity<Map<String,Object>> futureSensorDataGet(@RequestParam(value="sensor", defaultValue="")String sensor,
                                                                  @RequestParam(value="time1", defaultValue="")String time1,
                                                                  @RequestParam(value="time2", defaultValue="")String time2,
                                                                  @RequestParam(value="days", defaultValue="")String days) throws JSONException {
        return predictService.futureSensorDataGet(sensor, time1, time2, days);
    }

    // NEWDEAL 센서 Step2 데이터 호출
    @GetMapping("/sensorStepDataGet")
    public ResponseEntity<Map<String,Object>> sensorStepDataGet(@RequestParam(value="sensor", defaultValue="")String sensor,
                                                                  @RequestParam(value="time1", defaultValue="")String time1,
                                                                  @RequestParam(value="time2", defaultValue="")String time2,
                                                                @RequestParam(value="channelNumber", defaultValue="")String channelNumber) throws JSONException {
        return predictService.sensorStepDataGet(sensor, time1, time2, channelNumber); // 채널넘버 수정하기
    }

}


