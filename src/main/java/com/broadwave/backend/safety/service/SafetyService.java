package com.broadwave.backend.safety.service;

import com.broadwave.backend.Aws.AWSS3Service;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.safety.Safety;
import com.broadwave.backend.safety.SafetyRepository;
import com.broadwave.backend.safety.calculation.CalculationCapDto;
import com.broadwave.backend.safety.calculation.CalculationListDto;
import com.broadwave.backend.safety.calculation.CalculationRepository;
import com.broadwave.backend.safety.calculation.CalculationTempDto;
import com.broadwave.backend.safety.safetyDtos.SafetyInfoDto;
import com.broadwave.backend.safety.safetyDtos.SafetyListDto;
import com.broadwave.backend.safety.safetyDtos.SafetyMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : SafetyService
*/
@Slf4j
@Service
public class SafetyService {

    @Value("${newdeal.aws.s3.bucket.url}")
    private String AWSBUCKETURL;

    private final ModelMapper modelMapper;
    private final SafetyRepository safetyRepository;
    private final CalculationRepository calculationRepository;
    private final AWSS3Service awss3Service;

    @Autowired
    public SafetyService(ModelMapper modelMapper, SafetyRepository safetyRepository, CalculationRepository calculationRepository,
                         AWSS3Service awss3Service){
        this.modelMapper = modelMapper;
        this.safetyRepository = safetyRepository;
        this.calculationRepository = calculationRepository;
        this.awss3Service = awss3Service;
    }

    // NEWDEAL 계측 기반 안전성 추정 데이터 제공 저장
    public ResponseEntity<Map<String, Object>> safetySave(SafetyMapperDto safetyMapperDto, HttpServletRequest request) throws IOException {
        log.info("safetySave 호출성공");

        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        Safety safety;
        String fileName = UUID.randomUUID().toString().replace("-", "")+".png";

        if(safetyMapperDto.getId() == null){
            safetyMapperDto.setId(0L);
        }
        Optional<Safety> optionalSafety = safetyRepository.findById(safetyMapperDto.getId());
        if(optionalSafety.isPresent()){
            log.info("수정입니다.");
            safety = modelMapper.map(optionalSafety.get(), Safety.class);
            safety.setSfName(safetyMapperDto.getSfName());
            safety.setSfForm(safetyMapperDto.getSfForm());
            safety.setSfRank(safetyMapperDto.getSfRank());
            safety.setSfLength(safetyMapperDto.getSfLength());
            safety.setSfWidth(safetyMapperDto.getSfWidth());
            safety.setSfNum(safetyMapperDto.getSfNum());
            safety.setSfCompletionYear(safetyMapperDto.getSfCompletionYear());
            safety.setSfFactor(safetyMapperDto.getSfFactor());

            // AWS 첨부파일 수정
            if(safetyMapperDto.getSfImage() != null){
                if(safety.getSfFilePath() != null && safety.getSfFileName() != null){
                    String path = "/newdeal-img/"+safety.getSfFileYyyymmdd();
                    awss3Service.deleteObject(path,safety.getSfFileName());
                }

                safety.setSfFileName(fileName);
                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                safety.setSfFileYyyymmdd(date.format(new Date()));
                String filePath = "/newdeal-safety-images/" + date.format(new Date());
                log.info("filePath : "+AWSBUCKETURL+filePath+"/");
                safety.setSfFilePath(AWSBUCKETURL+filePath+"/");
                awss3Service.imageFileUpload(safetyMapperDto.getSfImage(), fileName, filePath);
            }

            safety.setModify_id(insert_id);
            safety.setModifyDateTime(LocalDateTime.now());
        }else{
            log.info("신규입니다.");
            safety = modelMapper.map(safetyMapperDto, Safety.class);

            // AWS 첨부파일 업로드
            if(safetyMapperDto.getSfImage() != null){
                safety.setSfFileName(fileName);
                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                safety.setSfFileYyyymmdd(date.format(new Date()));
                String filePath = "/newdeal-safety-images/" + date.format(new Date());
                log.info("filePath : "+AWSBUCKETURL+filePath+"/");
                safety.setSfFilePath(AWSBUCKETURL+filePath+"/");
                awss3Service.imageFileUpload(safetyMapperDto.getSfImage(), fileName, filePath);
            }

            safety.setInsert_id(insert_id);
            safety.setInsertDateTime(LocalDateTime.now());
        }
//        log.info("safety : "+safety);
        safetyRepository.save(safety);

        return ResponseEntity.ok(res.success());
    }


    // NEWDEAL 계측 기반 안전성 추정 데이터 - 교량 리스트 검색
    public ResponseEntity<Map<String, Object>> bridgeDataList(String sfForm, String sfRank, String sfName, HttpServletRequest request) {
        log.info("bridgeDataList 호출성공");

        log.info("sfForm : "+sfForm);
        log.info("sfRank : "+sfRank);
        log.info("sfName : "+sfName);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        List<SafetyListDto> safetyListDtoList = safetyRepository.findBySafetyList(sfForm, sfRank, sfName);
        data.put("gridListData",safetyListDtoList);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 계측 기반 안전성 추정 아웃풋
    public ResponseEntity<Map<String, Object>> safetyCalculationOutputInfo(Long id, HttpServletRequest request) {
        log.info("calculationDate 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        SafetyInfoDto safetyInfoDto = safetyRepository.findBySafetyInfo(id);
        data.put("safetyInfo",safetyInfoDto);

        List<CalculationListDto> calculationListDtoList = calculationRepository.findByCalculationList(id);
        data.put("gridListData",calculationListDtoList);

        List<CalculationTempDto> calculationTempDtos = calculationRepository.findByCalculationTempChart(id);
        data.put("temperatureData", calculationTempDtos);

        List<CalculationCapDto> calculationCapDtoList = calculationRepository.findByCalculationCapChart(id);
        data.put("capacityData",calculationCapDtoList);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


}
