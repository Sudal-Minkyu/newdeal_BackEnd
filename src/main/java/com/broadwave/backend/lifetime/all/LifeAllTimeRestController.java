package com.broadwave.backend.lifetime.all;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Remark : NEWDEAL 성능개선사업평가 전체부분 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/lifealltime")
public class LifeAllTimeRestController {

    private final LifeAllTimeService lifeAllTimeService;

    @Autowired
    public LifeAllTimeRestController(LifeAllTimeService lifeAllTimeService) {
        this.lifeAllTimeService = lifeAllTimeService;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 저장
    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute LifeAllTimeMapperDto lifeAllTimeMapperDto,HttpServletRequest request) {
        return lifeAllTimeService.save(lifeAllTimeMapperDto, request);
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 아웃풋
    @PostMapping("/output")
    public ResponseEntity<Map<String,Object>> output(@RequestParam(value="id", defaultValue="")Long id, HttpServletRequest request) {
        return lifeAllTimeService.output(id, request);
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 수정시 데이터 호출하기
    @PostMapping("/info")
    public ResponseEntity<Map<String,Object>> info(@RequestParam(value="ltId", defaultValue="")Long ltId) {
        return lifeAllTimeService.info(ltId);
    }













}
