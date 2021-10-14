package com.broadwave.backend.ltd.data.deformation;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author InSeok
 * Date : 2019-05-07
 * Time : 14:44
 * Remark :
 */
@Service
@Slf4j
public class DeformationRateService {

    private final DeformationRateRepository deformationRateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DeformationRateService(DeformationRateRepository deformationRateRepository, ModelMapper modelMapper) {
        this.deformationRateRepository = deformationRateRepository;
        this.modelMapper = modelMapper;
    }

    public List<DeformationRateDto> findBySeriesCode(SeriesCode seriesCode){
        log.info("측정항목(길이변형률) 데이터 가져오기 . 시리즈코드(시리즈명) : '" + seriesCode.getCode() + "'(" + seriesCode.getDesc()+ ")");
        List<DeformationRate> deformations = deformationRateRepository.findBySeriesCode(seriesCode);
        return deformations.stream().map(deformationRate -> modelMapper.map(deformationRate,DeformationRateDto.class)).collect(Collectors.toList());

    }
}
