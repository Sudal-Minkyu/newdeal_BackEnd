package com.broadwave.backend.env.weather;

import com.broadwave.backend.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2020-11-05
 * Time :
 * Remark :
 */
@RestController
@Slf4j
@RequestMapping("/api/env/weather")
public class WeatherRestController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherRestController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("list")
    public ResponseEntity<Map<String,Object>> findAll(){
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<WeatherDto> weathers = weatherService.findAll();
        List<List<String>> weathersArray = new ArrayList<>();

        weathers.forEach(weatherDto -> {
            weathersArray.add(weatherDto.toArray());
        });

        data.put("datalist",weathersArray);
        data.put("total_rows",weathersArray.size());

        log.info("기상관측소 정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


}
