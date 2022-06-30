package com.broadwave.backend.lifetime.detail.cabonationThreePlate;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.keygenerate.KeyGenerateService;
import com.broadwave.backend.lifetime.detail.main.LifeDetail;
import com.broadwave.backend.lifetime.detail.main.LiftDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2022-05-24
 * Time :
 * Remark : NewDeal LifeAllTime CabonationThreePlateService
*/
@Slf4j
@Service
public class CabonationThreePlateService {

    private final KeyGenerateService keyGenerateService;
    private final ModelMapper modelMapper;
    private final LiftDetailRepository liftDetailRepository;
    private final CabonationThreePlateRepository cabonationThreePlateRepository;

    @Autowired
    public CabonationThreePlateService(ModelMapper modelMapper, KeyGenerateService keyGenerateService, LiftDetailRepository liftDetailRepository,
                                       CabonationThreePlateRepository cabonationThreePlateRepository) {
        this.modelMapper = modelMapper;
        this.keyGenerateService = keyGenerateService;
        this.liftDetailRepository = liftDetailRepository;
        this.cabonationThreePlateRepository = cabonationThreePlateRepository;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 탄산화깊이 바닥판3개 저장
    public ResponseEntity<Map<String, Object>> cabonationThreePlateSave(CabonationThreePlateMapperDto cabonationThreePlateMapperDto, HttpServletRequest request) {
        log.info("cabonationThreePlateSave 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        CabonationThreePlate cabonationThreePlate = modelMapper.map(cabonationThreePlateMapperDto, CabonationThreePlate.class);

        // 고유코드가 존재하지 않으면 생성후 셋팅
        if(cabonationThreePlateMapperDto.getLtDetailAutonum().equals("")){
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String autoNum = keyGenerateService.keyGenerate("nd_lt_detail", yyMM.format(now), insert_id);

            LifeDetail lifeDetail = new LifeDetail();
            lifeDetail.setLtDetailType(cabonationThreePlateMapperDto.getLtDetailType());
            lifeDetail.setLtDetailAutoNum(autoNum);
            lifeDetail.setInsert_id(insert_id);
            lifeDetail.setInsertDateTime(LocalDateTime.now());

            cabonationThreePlate.setLtDetailAutoNum(autoNum);
            cabonationThreePlate.setInsert_id(insert_id);
            cabonationThreePlate.setInsertDateTime(LocalDateTime.now());

            liftDetailRepository.save(lifeDetail);
            cabonationThreePlateRepository.save(cabonationThreePlate);
            data.put("autoNum", autoNum);
        }else{
            Optional<LifeDetail> optionalLifeDetail = liftDetailRepository.findByLtDetailAutoNum(cabonationThreePlateMapperDto.getLtDetailAutonum());
            Optional<CabonationThreePlate> optionalCabonationThreePlate = cabonationThreePlateRepository.findByLtDetailAutoNum(cabonationThreePlateMapperDto.getLtDetailAutonum());

            if(optionalLifeDetail.isPresent()){
                optionalLifeDetail.get().setModify_id(insert_id);
                optionalLifeDetail.get().setModifyDateTime(LocalDateTime.now());
                data.put("autoNum",optionalLifeDetail.get().getLtDetailAutoNum());
                liftDetailRepository.save(optionalLifeDetail.get());
            }

            if(optionalCabonationThreePlate.isPresent()){
                cabonationThreePlate.setLtCarbonationThreePlateId(optionalCabonationThreePlate.get().getLtCarbonationThreePlateId());
                cabonationThreePlate.setModify_id(insert_id);
                cabonationThreePlate.setModifyDateTime(LocalDateTime.now());
                cabonationThreePlateRepository.save(cabonationThreePlate);
            }

        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
