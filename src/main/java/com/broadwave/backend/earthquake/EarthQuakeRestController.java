package com.broadwave.backend.earthquake;

import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-06-10
 * Remark : NEWDEAL 내진성능 추정서비스 Rest컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/earth")
public class EarthQuakeRestController {

    private final EarthQuekeService earthQuekeService;

    @Autowired
    public EarthQuakeRestController(EarthQuekeService earthQuekeService) {
        this.earthQuekeService = earthQuekeService;
    }

    // NEWDEAL 내진성능 추정서비스 단일 교량 수정 API
    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> update(@ModelAttribute EarthQuakeMapperDto earthQuakeMapperDto, HttpServletRequest request) {
        return earthQuekeService.update(earthQuakeMapperDto, request);
    }

    // NEWDEAL 내진성능 추정서비스 다중 교량 등록(엑셀 파일업로드 형식) API
    @PostMapping("/filesave")
    public ResponseEntity<Map<String,Object>> filesave(MultipartFile excelfile, HttpServletRequest request) throws IOException {
        return earthQuekeService.filesave(excelfile, request);
    }

    // NEWDEAL 내진성능 추정서비스 교량 리스트 호출 API
    @GetMapping("list")
    public ResponseEntity<Map<String,Object>> list(@RequestParam(value="eqBridge", defaultValue="") String eqBridge){
        return earthQuekeService.list(eqBridge);
    }

    // NEWDEAL 내진성능 추정 서비스 호출 API + 데이터 백앤드 계산 API
    @GetMapping("earthQuekePerformance")
    public ResponseEntity<Map<String,Object>> earthQuekePerformance(@RequestParam(value="eqBridge", defaultValue="") String eqBridge){
        return earthQuekeService.earthQuekePerformance(eqBridge);
    }


}


