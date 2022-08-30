package com.broadwave.backend.lifetime.detail.chloride;

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
 * Date : 2022-08-04
 * Time :
 * Remark : NewDeal LifeAllTime CrackService
*/
@Slf4j
@Service
public class ChlorideService {

    private final KeyGenerateService keyGenerateService;
    private final ModelMapper modelMapper;
    private final LiftDetailRepository liftDetailRepository;
    private final ChlorideRepository chlorideRepository;

    @Autowired
    public ChlorideService(ModelMapper modelMapper, KeyGenerateService keyGenerateService, LiftDetailRepository liftDetailRepository,
                           ChlorideRepository chlorideRepository) {
        this.modelMapper = modelMapper;
        this.keyGenerateService = keyGenerateService;
        this.liftDetailRepository = liftDetailRepository;
        this.chlorideRepository = chlorideRepository;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 열화물침투량 저장
    public ResponseEntity<Map<String,Object>> chlorideSave(ChlorideMapperDto chlorideMapperDto, HttpServletRequest request){

        log.info("chlorideSave 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        Chloride chloride = modelMapper.map(chlorideMapperDto, Chloride.class);

        // 고유코드가 존재하지 않으면 생성후 셋팅
        if(chlorideMapperDto.getLtDetailAutonum().equals("")){
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String autoNum = keyGenerateService.keyGenerate("nd_lt_detail", yyMM.format(now), insert_id);

            LifeDetail lifeDetail = new LifeDetail();
            lifeDetail.setLtDetailType(chlorideMapperDto.getLtDetailType());
            lifeDetail.setLtDetailAutoNum(autoNum);
            lifeDetail.setInsert_id(insert_id);
            lifeDetail.setInsertDateTime(LocalDateTime.now());

            chloride.setLtDetailAutoNum(autoNum);
            chloride.setInsert_id(insert_id);
            chloride.setInsertDateTime(LocalDateTime.now());

            liftDetailRepository.save(lifeDetail);
            chlorideRepository.save(chloride);
            data.put("autoNum", autoNum);
        }else{
            Optional<LifeDetail> optionalLifeDetail = liftDetailRepository.findByLtDetailAutoNum(chlorideMapperDto.getLtDetailAutonum());
            Optional<Chloride> optionalChloride = chlorideRepository.findByLtDetailAutoNum(chlorideMapperDto.getLtDetailAutonum());

            if(optionalLifeDetail.isPresent()){
                optionalLifeDetail.get().setModify_id(insert_id);
                optionalLifeDetail.get().setModifyDateTime(LocalDateTime.now());
                data.put("autoNum",optionalLifeDetail.get().getLtDetailAutoNum());
                liftDetailRepository.save(optionalLifeDetail.get());
            }

            if(optionalChloride.isPresent()){
                chloride.setLtChlorideId(optionalChloride.get().getLtChlorideId());
                chloride.setModify_id(insert_id);
                chloride.setModifyDateTime(LocalDateTime.now());
                chlorideRepository.save(chloride);
            }

        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
