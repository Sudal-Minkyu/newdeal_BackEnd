package com.broadwave.backend.lifetime.detail.hardness;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.keygenerate.KeyGenerateService;
import com.broadwave.backend.lifetime.detail.carbonation.Cabonation;
import com.broadwave.backend.lifetime.detail.carbonation.CabonationMapperDto;
import com.broadwave.backend.lifetime.detail.carbonation.CabonationRepository;
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
 * Date : 2022-07-14
 * Time :
 * Remark : NewDeal LifeAllTime HardnessService
*/
@Slf4j
@Service
public class HardnessService {

    private final KeyGenerateService keyGenerateService;
    private final ModelMapper modelMapper;
    private final LiftDetailRepository liftDetailRepository;
    private final HardnessRepository hardnessRepository;

    @Autowired
    public HardnessService(ModelMapper modelMapper, KeyGenerateService keyGenerateService, LiftDetailRepository liftDetailRepository,
                           HardnessRepository hardnessRepository) {
        this.modelMapper = modelMapper;
        this.keyGenerateService = keyGenerateService;
        this.liftDetailRepository = liftDetailRepository;
        this.hardnessRepository = hardnessRepository;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 반발경도 저장
    public ResponseEntity<Map<String,Object>> hardnessSave(HardnessMapperDto hardnessMapperDto, HttpServletRequest request){

        log.info("hardnessSave 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        Hardness hardness = modelMapper.map(hardnessMapperDto, Hardness.class);

        // 고유코드가 존재하지 않으면 생성후 셋팅
        if(hardnessMapperDto.getLtDetailAutonum().equals("")){
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String autoNum = keyGenerateService.keyGenerate("nd_lt_detail", yyMM.format(now), insert_id);

            LifeDetail lifeDetail = new LifeDetail();
            lifeDetail.setLtDetailType(hardnessMapperDto.getLtDetailType());
            lifeDetail.setLtDetailAutoNum(autoNum);
            lifeDetail.setInsert_id(insert_id);
            lifeDetail.setInsertDateTime(LocalDateTime.now());

            hardness.setLtDetailAutoNum(autoNum);
            hardness.setInsert_id(insert_id);
            hardness.setInsertDateTime(LocalDateTime.now());

            liftDetailRepository.save(lifeDetail);
            hardnessRepository.save(hardness);
            data.put("autoNum", autoNum);
        }else{
            Optional<LifeDetail> optionalLifeDetail = liftDetailRepository.findByLtDetailAutoNum(hardnessMapperDto.getLtDetailAutonum());
            Optional<Hardness> optionalHardness = hardnessRepository.findByLtDetailAutoNum(hardnessMapperDto.getLtDetailAutonum());

            if(optionalLifeDetail.isPresent()){
                optionalLifeDetail.get().setModify_id(insert_id);
                optionalLifeDetail.get().setModifyDateTime(LocalDateTime.now());
                data.put("autoNum",optionalLifeDetail.get().getLtDetailAutoNum());
                liftDetailRepository.save(optionalLifeDetail.get());
            }

            if(optionalHardness.isPresent()){
                hardness.setLtHardnessId(optionalHardness.get().getLtHardnessId());
                hardness.setModify_id(insert_id);
                hardness.setModifyDateTime(LocalDateTime.now());
                hardnessRepository.save(hardness);
            }

        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
