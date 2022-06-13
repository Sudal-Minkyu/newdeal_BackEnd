package com.broadwave.backend.saltpermeate;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.saltpermeate.saltpermeateDtos.SaltPermeateListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2021-06-13
 * Time :
 * Remark : NewDeal SaltPermeate Service
*/
@Slf4j
@Service
public class SaltPermeateService {

    private final SaltPermeateRepository saltPermeateRepository;

    @Autowired
    public SaltPermeateService(SaltPermeateRepository saltPermeateRepository){
        this.saltPermeateRepository = saltPermeateRepository;
    }

    // NEWDEAL 열화환경별 염화물 교량 리스트 호출 API
    public ResponseEntity<Map<String, Object>> list(String stBridge) {
        log.info("list 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<SaltPermeateListDto> saltPermeateListDtoList = saltPermeateRepository.findByStBridge(stBridge);
        data.put("gridListData",saltPermeateListDtoList);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
