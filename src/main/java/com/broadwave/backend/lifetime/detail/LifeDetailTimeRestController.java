package com.broadwave.backend.lifetime.detail;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.lifetime.absence.AbsenceService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2021-09-13
 * Remark : NEWDEAL 성능개선사업평가 세부부분 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/lifedetailtime")
public class LifeDetailTimeRestController {

    private final ModelMapper modelMapper;
    private final AbsenceService absenceService;
    private final LifeDetailTimeService lifeDetailTimeService;

    @Autowired
    public LifeDetailTimeRestController(ModelMapper modelMapper, AbsenceService absenceService, LifeDetailTimeService lifeDetailTimeService) {
        this.modelMapper = modelMapper;
        this.absenceService = absenceService;
        this.lifeDetailTimeService = lifeDetailTimeService;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 저장
    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute LifeDetailTimeMapperDto lifeDetailTimeMapperDto, HttpServletRequest request) {

        log.info("save 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        LifeDetailTime lifeDetailTime = modelMapper.map(lifeDetailTimeMapperDto, LifeDetailTime.class);

        lifeDetailTime.setInsertDateTime(LocalDateTime.now());
        lifeDetailTime.setInsert_id(insert_id);

        log.info("lifeDetailTime : "+lifeDetailTime);

        LifeDetailTime detailTime = lifeDetailTimeService.save(lifeDetailTime);

        data.put("getId", detailTime.getId());

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 아웃풋
    @PostMapping("/output")
    public ResponseEntity<Map<String,Object>> output(@RequestParam(value="id", defaultValue="")Long id, HttpServletRequest request) {

        log.info("output 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        LifeDetailTimeDto lifeDetailTimeDto = lifeDetailTimeService.findById(id);

        if(lifeDetailTimeDto.getId() != null){

            log.info("lifeDetailTimeDto : "+lifeDetailTimeDto);

            List<Double> meanList = new ArrayList<>();
            List<Double> sdtList = new ArrayList<>();

            double areAverage = 0.015; // 철근 단면적 감소율 평균값
            double areVariance = 0.1; // 철근 단면적 감소율 변동계수
            for(int i=0; i<20; i++){
                if(i==0){
                    meanList.add(1.0);
                    sdtList.add(0.1);
                }else{
                    double mean = meanList.get(i-1)-(meanList.get(i-1) * areAverage);
                    double sdt = mean * areVariance;
                    meanList.add(mean);
                    sdtList.add(sdt);
                }
            }
            log.info("meanList : "+meanList);
            log.info("sdtList : "+sdtList);

            // 사용자 입력 난수 -  평균값
            double fyAverage = lifeDetailTimeDto.getLtFyAverage(); // 철근 항복강도 평균값
            double AAverage = lifeDetailTimeDto.getLtSectionAverage(); // 철근 단면적 평균값
            double fcAverage = lifeDetailTimeDto.getLtFcAverage(); // 콘크리트 압축강도 평균값
            double mdckAverage = lifeDetailTimeDto.getLtVehicleAverage(); // 차량하중 평균값
            // 사용자 입력 난수 -  표준편차
            double fyStandard = lifeDetailTimeDto.getLtFyStandard(); // 철근 항복강도 표준편차
            double AStandard = lifeDetailTimeDto.getLtSectionStandard(); // 철근 단면적 표준편차
            double fcStandard = lifeDetailTimeDto.getLtFcStandard(); // 콘크리트 압축강도 표준편차
            double mdckStandard = lifeDetailTimeDto.getLtVehicleStandard(); // 차량하중 표준편차

            // 시스템 난수 -  평균값
            double ymfcAverage = 1.02; // 바닥판 모델링 불확실성 인자 평균값
            double asphAverage = 1.0; // 아스콘 중량 불확실성 인자 평균값
            double concAverage = 1.05; // 콘크리트 중량 불확실성 인자 평균값
            // 시스템 난수 -  표준편차
            double ymfcStandard = 0.06; // 바닥판 모델링 불확실성 인자 표준편차
            double asphStandard= 0.25; // 아스콘 중량 불확실성 인자 표준편차
            double concStandard = 0.11; // 콘크리트 중량 불확실성 인자 표준편차

            // Are 난수
            double areRandom;

            double a;
            log.info("테스트입니다 : ");
            int simulation = 101; // 시뮬레이션 횟수
//            for(int i=1; i<2; i++){
//                double rand = Math.random();
////                double fyNum = NORMINV(rand,fyAverage,fyStandard); // fy 난수
////                log.info("fyNum 난수 : "+fyNum);
//            }

//            data.put("chartDataList",chartDataList);

        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }

//        data.put("lifeDetailTimeDto",lifeDetailTimeDto);

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

}


