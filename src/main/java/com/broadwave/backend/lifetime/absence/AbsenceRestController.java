package com.broadwave.backend.lifetime.absence;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.performance.price.PriceDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Remark : NEWDEAL 부재별 평균열화율 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/absence")
public class AbsenceRestController {

    private final ModelMapper modelMapper;
    private final AbsenceService absenceService;

    @Autowired
    public AbsenceRestController(ModelMapper modelMapper, AbsenceService absenceService) {
        this.modelMapper = modelMapper;
        this.absenceService = absenceService;
    }

//  NEWDEAL 생애주기 교량 부재별 평균열화율 수치 수정 및저장
    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> absenceSave(@ModelAttribute AbsenceMapperDto absenceMapperDto, HttpServletRequest request) {

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        Absence absence = modelMapper.map(absenceMapperDto, Absence.class);
        Optional<Absence> optionalAbsence = absenceService.findByAbsenceCode(absenceMapperDto.getLtAbsenceCode());

        if(optionalAbsence.isPresent()){
            log.info("수정 입니다.");
            absence.setId(optionalAbsence.get().getId());
            absence.setInsertDateTime(optionalAbsence.get().getInsertDateTime());
            absence.setInsertId(optionalAbsence.get().getInsertId());
            absence.setModifyDateTime(LocalDateTime.now());
            absence.setModifyId(insert_id);
        }else{
            log.info("저장 입니다.");
            absence.setInsertDateTime(LocalDateTime.now());
            absence.setInsertId(insert_id);
            absence.setModifyDateTime(null);
            absence.setModifyId(null);
        }

        absenceService.save(absence);

        return ResponseEntity.ok(res.success());

    }

    // NEWDEAL 생애주기 교량 부재별 평균열화율 수치 리스트 조회
    @GetMapping("list")
    public ResponseEntity<Map<String,Object>> absenceList(@RequestParam("ltAbsence")String ltAbsence,
                                                        @RequestParam("ltAbsenceCode")String ltAbsenceCode,
                                                        @RequestParam("ltAbsenceName")String ltAbsenceName,
                                                        Pageable pageable){
        AjaxResponse res = new AjaxResponse();
        Page<AbsenceListDto> absenceListDto = absenceService.findByAbsenceList(ltAbsence, ltAbsenceCode, ltAbsenceName, pageable);
//        log.info("absenceListDto : "+absenceListDto.getContent());
        return ResponseEntity.ok(res.ResponseEntityPage(absenceListDto));
    }

    // NEWDEAL 생애주기 교량 부재별 평균열화율 수치 정보 조회
    @GetMapping("info")
    public ResponseEntity<Map<String,Object>> absenceInfo(@RequestParam("id")Long id){
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<Absence> absence = absenceService.findById(id);
//        log.info("absence : "+absence);

        data.put("absence", absence);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 생애주기 교량 부재별 평균열화율 수치 삭제
    @PostMapping("del")
    public ResponseEntity<Map<String,Object>> absenceDel(@RequestParam("id")Long id){
        AjaxResponse res = new AjaxResponse();

        Optional<Absence> absence = absenceService.findById(id);
//        log.info("absence : "+absence);
        absence.ifPresent(absenceService::delete);

        return ResponseEntity.ok(res.success());
    }


}
