package com.broadwave.backend.earthquake;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeDto;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeListDto;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-06-10
 * Time :
 * Remark : NewDeal EarthQueke Service
*/
@Slf4j
@Service
public class EarthQuekeService {

    private final EarthQuakeRepository earthQuakeRepository;

    @Autowired
    public EarthQuekeService(EarthQuakeRepository earthQuakeRepository){
        this.earthQuakeRepository = earthQuakeRepository;
    }

    // NEWDEAL 내진성능 추정서비스 단일 교량 등록
    public ResponseEntity<Map<String, Object>> save(EarthQuakeMapperDto earthQuakeMapperDto, HttpServletRequest request) {
        log.info("save 호출");

        AjaxResponse res = new AjaxResponse();


        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 내진성능 추정서비스 다중 교량 등록(엑셀 파일업로드 형식)
    public ResponseEntity<Map<String, Object>> multisave(MultipartHttpServletRequest multi) {
        log.info("multisave 호출");

        AjaxResponse res = new AjaxResponse();


        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 내진성능 추정서비스 교량 리스트 호출 API
    public ResponseEntity<Map<String, Object>> list(String eqLocation, String eqBridge) {
        log.info("list 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<EarthQuakeListDto> earthQuakeDtoList = earthQuakeRepository.findByEarthQuake(eqLocation, eqBridge);
        data.put("gridListData",earthQuakeDtoList);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 내진성능 추정 서비스 호출 API + 데이터 백앤드 계산 API
    public ResponseEntity<Map<String, Object>> earthQuekePerformance(String eqBridge) {
        log.info("earthQuekePerformance 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        EarthQuakeDto earthQuakeDto = earthQuakeRepository.findByEqBridge(eqBridge);
        if(earthQuakeDto == null){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(), ResponseErrorCode.NDE006.getDesc(), ResponseErrorCode.NDE031.getCode(), ResponseErrorCode.NDE031.getDesc()));
        }else{
            data.put("earthQuakeDto",earthQuakeDto);
        }






        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


}
