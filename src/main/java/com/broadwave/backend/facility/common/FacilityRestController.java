package com.broadwave.backend.facility.common;

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
 * Date : 2019-10-14
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/facility/common")
public class FacilityRestController {

    private final FacilityService facilityService;

    @Autowired
    public FacilityRestController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @PostMapping("mapList")
    public ResponseEntity<Map<String,Object>> findAll(){

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<FacilityDto> bridges = facilityService.findAll();
        List<List<String>> bridgesArray = new ArrayList<>();

        bridges.forEach(bridgeDto -> {
            bridgesArray.add(bridgeDto.toArray());
        });

        data.put("datalist",bridgesArray);
        data.put("total_rows",bridgesArray.size());

        log.info("기상관측소 정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
