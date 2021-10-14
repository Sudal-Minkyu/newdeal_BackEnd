package com.broadwave.backend.ltd.data.chloride;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author InSeok
 * Date : 2019-05-10
 * Time : 11:27
 * Remark :
 */
@Service
@Slf4j
public class PenetratedChlorideService {
    private final PenetratedChlorideRepository penetratedChlorideRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PenetratedChlorideService(PenetratedChlorideRepository penetratedChlorideRepository, ModelMapper modelMapper) {
        this.penetratedChlorideRepository = penetratedChlorideRepository;
        this.modelMapper = modelMapper;
    }

    public List<PenetratedChlorideDto> findBySeriesCode(SeriesCode seriesCode){
        log.info("측정항목(염분함유량) 데이터 가져오기 . 시리즈코드(시리즈명) : '" + seriesCode.getCode() + "'(" + seriesCode.getDesc()+ ")");
        List<PenetratedChloride> penetratedChlorides = penetratedChlorideRepository.findBySeriesCode(seriesCode);
        return penetratedChlorides.stream().map(penetratedChloride -> modelMapper.map(penetratedChloride, PenetratedChlorideDto.class)).collect(Collectors.toList());
    }
}
