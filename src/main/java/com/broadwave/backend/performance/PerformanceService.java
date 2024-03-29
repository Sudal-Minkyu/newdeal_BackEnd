package com.broadwave.backend.performance;

import com.broadwave.backend.performance.performanceDtos.*;
import com.broadwave.backend.performance.uploadFile.Uploadfile;
import com.broadwave.backend.performance.uploadFile.UploadfileRepository;
import com.broadwave.backend.python.PythonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-07-09
 * Time :
 * Remark : NewDeal Performance Service
*/
@Slf4j
@Service
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceRepositoryCustom performanceRepositoryCustom;
    private final UploadfileRepository uploadfileRepository;

    @Autowired
    public PerformanceService(PerformanceRepository performanceRepository,  PerformanceRepositoryCustom performanceRepositoryCustom, UploadfileRepository uploadfileRepository) {
        this.performanceRepository = performanceRepository;
        this.performanceRepositoryCustom = performanceRepositoryCustom;
        this.uploadfileRepository = uploadfileRepository;
    }


    public void save(Performance performance){
        performanceRepository.save(performance);
    }

    public List<PerformanceDto> findByAutoNum(String autoNum) {
        return performanceRepositoryCustom.findByAutoNum(autoNum);
    }

    public PerformanceCheckDto findByInsertId(String insert_id) {
        return performanceRepositoryCustom.findByInsertId(insert_id);
    }

    public PerformanceMiddleDataDto findByInsertIAndAutoNum(String insert_id, String autoNum) {
        return performanceRepositoryCustom.findByInsertIAndAutoNum(insert_id,autoNum);
    }

    public Optional<Performance> findByPiAutoNumAndInsert_id(String autoNum, String insert_id) {
        return performanceRepository.findByPiAutoNumAndInsert_id(autoNum,insert_id);
    }

    public Performance findByBusiness(String autoNum, String insert_id) {
        return performanceRepositoryCustom.findByBusiness(autoNum,insert_id);
    }

    public List<Performance> findByPiAutoNumAndInsert_idDel(String autoNum, String insert_id,Integer piInputMiddleSave) {
        return performanceRepositoryCustom.findByPiAutoNumAndInsert_idDel(autoNum,insert_id,piInputMiddleSave);
    }

    public Optional<Performance> findByPiAutoNumAndInsert_idAndPiInputCount(String autoNum, Integer piInputCount) {
        return performanceRepository.findByPiAutoNumAndInsert_idAndPiInputCount(autoNum,piInputCount);
    }

    public void delete(Performance performance) {
        performanceRepository.delete(performance);
    }

    public List<PerformanceMiddleBusinessDataDto> findByInsertIAndAutoNum2(String insert_id, String autoNum) {
        return performanceRepositoryCustom.findByInsertIAndAutoNum2(insert_id,autoNum);
    }

    public Optional<Performance> findById(Long id) {
        return performanceRepository.findById(id);
    }

    public PerformancePiBusinessDto findByInsertIAndAutoNumAndCount(String insert_id, String autoNum) {
        return performanceRepositoryCustom.findByInsertIAndAutoNumAndCount(insert_id,autoNum);
    }

    public Page<PerformanceListDto> findByPerformanceList(String piFacilityType, String piKind, String piFacilityName, String insert_id, Pageable pageable) {
        return performanceRepositoryCustom.findByPerformanceList(piFacilityType, piKind, piFacilityName, insert_id, pageable);
    }

    public void findByPerformanceListDelete(List<Performance> performanceList) {
        performanceRepository.deleteAll(performanceList);
    }

    public void saveUploadFile(Uploadfile uploadfile) {
        uploadfileRepository.save(uploadfile);
    }

    public void deleteUploadFile(Uploadfile uploadfile) {
        uploadfileRepository.delete(uploadfile);
    }


    public Optional<Uploadfile> findByUploadFile(String autoNum, String insert_id) {
        return uploadfileRepository.findByUploadFile(autoNum,insert_id);
    }

}
