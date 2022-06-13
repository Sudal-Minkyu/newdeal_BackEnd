package com.broadwave.backend.saltpermeate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-06-13
 * Remark : NEWDEAL 열화환경별 염화물 Rest컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/salt")
public class SaltPermeateRestController {

    private final SaltPermeateService saltPermeateService;

    @Autowired
    public SaltPermeateRestController(SaltPermeateService saltPermeateService) {
        this.saltPermeateService = saltPermeateService;
    }

    // NEWDEAL 열화환경별 염화물 교량 리스트 호출 API
    @GetMapping("list")
    public ResponseEntity<Map<String,Object>> list(@RequestParam(value="stBridge", defaultValue="") String stBridge){
        return saltPermeateService.list(stBridge);
    }

}


