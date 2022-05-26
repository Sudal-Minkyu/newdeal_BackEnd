package com.broadwave.backend.lifetime.detail.carbonation;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.keygenerate.KeyGenerateService;
import com.broadwave.backend.lifetime.detail.LifeDetail;
import com.broadwave.backend.lifetime.detail.LiftDetailRepository;
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
 * Date : 2021-09-15
 * Time :
 * Remark : NewDeal LifeAllTime Service
*/
@Slf4j
@Service
public class CabonationService {

    private final KeyGenerateService keyGenerateService;
    private final ModelMapper modelMapper;
    private final  LiftDetailRepository liftDetailRepository;
    private final  CabonationRepository cabonationRepository;

    @Autowired
    public CabonationService(ModelMapper modelMapper, KeyGenerateService keyGenerateService, LiftDetailRepository liftDetailRepository,
                             CabonationRepository cabonationRepository) {
        this.modelMapper = modelMapper;
        this.keyGenerateService = keyGenerateService;
        this.liftDetailRepository = liftDetailRepository;
        this.cabonationRepository = cabonationRepository;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 탄산화깊이 저장
    public ResponseEntity<Map<String,Object>> cabonationSave(CabonationMapperDto cabonationMapperDto, HttpServletRequest request){

        log.info("cabonationSave 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        Cabonation cabonation = modelMapper.map(cabonationMapperDto, Cabonation.class);

        // 고유코드가 존재하지 않으면 생성후 셋팅
        if(cabonationMapperDto.getLtDetailAutonum().equals("")){
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String autoNum = keyGenerateService.keyGenerate("nd_lt_detail", yyMM.format(now), insert_id);

            LifeDetail lifeDetail = new LifeDetail();
            lifeDetail.setLtDetailType(cabonationMapperDto.getLtDetailType());
            lifeDetail.setLtDetailAutoNum(autoNum);
            lifeDetail.setInsert_id(insert_id);
            lifeDetail.setInsertDateTime(LocalDateTime.now());

            cabonation.setLtDetailAutoNum(autoNum);
            cabonation.setInsert_id(insert_id);
            cabonation.setInsertDateTime(LocalDateTime.now());

            liftDetailRepository.save(lifeDetail);
            cabonationRepository.save(cabonation);
            data.put("autoNum", autoNum);
        }else{
            Optional<LifeDetail> optionalLifeDetail = liftDetailRepository.findByLtDetailAutoNum(cabonationMapperDto.getLtDetailAutonum());
            Optional<Cabonation> optionalCabonation = cabonationRepository.findByLtDetailAutoNum(cabonationMapperDto.getLtDetailAutonum());

            if(optionalLifeDetail.isPresent()){
                optionalLifeDetail.get().setModify_id(insert_id);
                optionalLifeDetail.get().setModifyDateTime(LocalDateTime.now());
                data.put("autoNum",optionalLifeDetail.get().getLtDetailAutoNum());
                liftDetailRepository.save(optionalLifeDetail.get());
            }

            if(optionalCabonation.isPresent()){
                cabonation.setLtCarbonationId(optionalCabonation.get().getLtCarbonationId());
                cabonation.setModify_id(insert_id);
                cabonation.setModifyDateTime(LocalDateTime.now());
                cabonationRepository.save(cabonation);
            }

        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }






}
