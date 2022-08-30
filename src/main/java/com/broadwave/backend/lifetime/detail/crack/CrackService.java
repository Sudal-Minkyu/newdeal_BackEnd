package com.broadwave.backend.lifetime.detail.crack;

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
public class CrackService {

    private final KeyGenerateService keyGenerateService;
    private final ModelMapper modelMapper;
    private final LiftDetailRepository liftDetailRepository;
    private final CrackRepository crackRepository;

    @Autowired
    public CrackService(ModelMapper modelMapper, KeyGenerateService keyGenerateService, LiftDetailRepository liftDetailRepository,
                        CrackRepository crackRepository) {
        this.modelMapper = modelMapper;
        this.keyGenerateService = keyGenerateService;
        this.liftDetailRepository = liftDetailRepository;
        this.crackRepository = crackRepository;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 세부부분 - 균열깊이 저장
    public ResponseEntity<Map<String,Object>> crackSave(CrackMapperDto crackMapperDto, HttpServletRequest request){

        log.info("crackSave 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        Crack crack = modelMapper.map(crackMapperDto, Crack.class);

        // 고유코드가 존재하지 않으면 생성후 셋팅
        if(crackMapperDto.getLtDetailAutonum().equals("")){
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String autoNum = keyGenerateService.keyGenerate("nd_lt_detail", yyMM.format(now), insert_id);

            LifeDetail lifeDetail = new LifeDetail();
            lifeDetail.setLtDetailType(crackMapperDto.getLtDetailType());
            lifeDetail.setLtDetailAutoNum(autoNum);
            lifeDetail.setInsert_id(insert_id);
            lifeDetail.setInsertDateTime(LocalDateTime.now());

            crack.setLtDetailAutoNum(autoNum);
            crack.setInsert_id(insert_id);
            crack.setInsertDateTime(LocalDateTime.now());

            liftDetailRepository.save(lifeDetail);
            crackRepository.save(crack);
            data.put("autoNum", autoNum);
        }else{
            Optional<LifeDetail> optionalLifeDetail = liftDetailRepository.findByLtDetailAutoNum(crackMapperDto.getLtDetailAutonum());
            Optional<Crack> optionalCrack = crackRepository.findByLtDetailAutoNum(crackMapperDto.getLtDetailAutonum());

            if(optionalLifeDetail.isPresent()){
                optionalLifeDetail.get().setModify_id(insert_id);
                optionalLifeDetail.get().setModifyDateTime(LocalDateTime.now());
                data.put("autoNum",optionalLifeDetail.get().getLtDetailAutoNum());
                liftDetailRepository.save(optionalLifeDetail.get());
            }

            if(optionalCrack.isPresent()){
                crack.setLtCrackId(optionalCrack.get().getLtCrackId());
                crack.setModify_id(insert_id);
                crack.setModifyDateTime(LocalDateTime.now());
                crackRepository.save(crack);
            }

        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
