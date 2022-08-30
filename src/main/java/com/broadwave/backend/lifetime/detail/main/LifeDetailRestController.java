package com.broadwave.backend.lifetime.detail.main;

import com.broadwave.backend.lifetime.detail.cabonationThreePlate.CabonationThreePlateMapperDto;
import com.broadwave.backend.lifetime.detail.cabonationThreePlate.CabonationThreePlateService;
import com.broadwave.backend.lifetime.detail.carbonation.CabonationMapperDto;
import com.broadwave.backend.lifetime.detail.carbonation.CabonationService;
import com.broadwave.backend.lifetime.detail.chloride.ChlorideMapperDto;
import com.broadwave.backend.lifetime.detail.chloride.ChlorideService;
import com.broadwave.backend.lifetime.detail.crack.CrackMapperDto;
import com.broadwave.backend.lifetime.detail.crack.CrackService;
import com.broadwave.backend.lifetime.detail.hardness.HardnessMapperDto;
import com.broadwave.backend.lifetime.detail.hardness.HardnessService;
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
    private final HardnessService hardnessSave;
    private final CabonationService cabonationService;
    private final CrackService crackService;
    private final ChlorideService chlorideService;
    private final CabonationThreePlateService cabonationThreePlateService;

    @Autowired
    public LifeDetailRestController(LifeDetailService lifeDetailService, HardnessService hardnessSave, CabonationService cabonationService,
                                    CrackService crackService, ChlorideService chlorideService, CabonationThreePlateService cabonationThreePlateService) {
        this.lifeDetailService = lifeDetailService;
        this.hardnessSave = hardnessSave;
        this.cabonationService = cabonationService;
        this.crackService = crackService;
        this.chlorideService = chlorideService;
        this.cabonationThreePlateService = cabonationThreePlateService;
    }

// @@@@@@@@@@@@@@ 인풋 페이지 관련 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 반발경도 저장
    @PostMapping("/hardness/save")
    public ResponseEntity<Map<String,Object>> hardnessSave(@ModelAttribute HardnessMapperDto hardnessMapperDto, HttpServletRequest request) {
        return hardnessSave.hardnessSave(hardnessMapperDto, request);
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 탄산화깊이 저장
    @PostMapping("/cabonation/save")
    public ResponseEntity<Map<String,Object>> cabonationSave(@ModelAttribute CabonationMapperDto cabonationMapperDto, HttpServletRequest request) {
        return cabonationService.cabonationSave(cabonationMapperDto, request);
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 균열깊이 저장
    @PostMapping("/crack/save")
    public ResponseEntity<Map<String,Object>> cabonationSave(@ModelAttribute CrackMapperDto crackMapperDto, HttpServletRequest request) {
        return crackService.crackSave(crackMapperDto, request);
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 열화물침투량 저장
    @PostMapping("/chloride/save")
    public ResponseEntity<Map<String,Object>> chlorideSave(@ModelAttribute ChlorideMapperDto chlorideMapperDto, HttpServletRequest request) {
        return chlorideService.chlorideSave(chlorideMapperDto, request);
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


