package com.broadwave.backend.lifetime.detail.main;

import com.broadwave.backend.lifetime.detail.cabonationThreePlate.CabonationThreePlateMapperDto;
import com.broadwave.backend.lifetime.detail.cabonationThreePlate.CabonationThreePlateService;
import com.broadwave.backend.lifetime.detail.carbonation.CabonationMapperDto;
import com.broadwave.backend.lifetime.detail.carbonation.CabonationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-05-23
 * Remark : NEWDEAL 성능개선사업평가 세부부분 Rest컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/lifedetail")
public class LifeDetailRestController {

    private final LifeDetailService lifeDetailService;
    private final CabonationService cabonationService;
    private final CabonationThreePlateService cabonationThreePlateService;

    @Autowired
    public LifeDetailRestController(LifeDetailService lifeDetailService, CabonationService cabonationService, CabonationThreePlateService cabonationThreePlateService) {
        this.lifeDetailService = lifeDetailService;
        this.cabonationService = cabonationService;
        this.cabonationThreePlateService = cabonationThreePlateService;
    }

// @@@@@@@@@@@@@@ 인풋 페이지 관련 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 탄산화깊이 저장
    @PostMapping("/cabonation/save")
    public ResponseEntity<Map<String,Object>> cabonationSave(@ModelAttribute CabonationMapperDto cabonationMapperDto, HttpServletRequest request) {
        return cabonationService.cabonationSave(cabonationMapperDto, request);
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 탄산화깊이 바닥판3개 저장
    @PostMapping("/cabonationThreePlate/save")
    public ResponseEntity<Map<String,Object>> cabonationThreePlateSave(@ModelAttribute CabonationThreePlateMapperDto cabonationThreePlateMapperDto, HttpServletRequest request) {
        return cabonationThreePlateService.cabonationThreePlateSave(cabonationThreePlateMapperDto, request);
    }

// @@@@@@@@@@@@@@ 아웃풋 페이지 관련 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 아웃풋
    @PostMapping("/output")
    public ResponseEntity<Map<String,Object>> output(@RequestParam(value="autoNum", defaultValue="")String autoNum, HttpServletRequest request) throws ClassNotFoundException {
        return lifeDetailService.output(autoNum, request);
    }

}


