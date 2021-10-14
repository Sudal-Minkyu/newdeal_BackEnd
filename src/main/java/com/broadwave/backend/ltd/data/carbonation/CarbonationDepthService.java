package com.broadwave.backend.ltd.data.carbonation;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author InSeok
 * Date : 2019-05-08
 * Time : 11:12
 * Remark :
 */
@Service
@Slf4j
public class CarbonationDepthService {

    private final CarbonationDepthRepository carbonationDepthRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarbonationDepthService(CarbonationDepthRepository carbonationDepthRepository, ModelMapper modelMapper) {
        this.carbonationDepthRepository = carbonationDepthRepository;
        this.modelMapper = modelMapper;
    }

    public List<CarbonationDepthDto> findBySeriesCode(SeriesCode seriesCode){
        log.info("측정항목(탄산화깊이) 데이터 가져오기 . 시리즈코드(시리즈명) : '" + seriesCode.getCode() + "'(" + seriesCode.getDesc()+ ")");
        List<CarbonationDepth> carbonations = carbonationDepthRepository.findBySeriesCode(seriesCode);
        return carbonations.stream().map(carbonation -> modelMapper.map(carbonation,CarbonationDepthDto.class)).collect(Collectors.toList());
    }

}
