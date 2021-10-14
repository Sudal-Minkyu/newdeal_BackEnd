package com.broadwave.backend.ltd.data.area;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author InSeok
 * Date : 2019-05-09
 * Time : 16:44
 * Remark :
 */
@Service
@Slf4j
public class CorrosionAreaService {

    private final CorrosionAreaRepository corrosionAreaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CorrosionAreaService(CorrosionAreaRepository corrosionAreaRepository, ModelMapper modelMapper) {
        this.corrosionAreaRepository = corrosionAreaRepository;
        this.modelMapper = modelMapper;
    }

    public List<CorrosionAreaDto> findBySeriesCode(SeriesCode seriesCode){
        log.info("측정항목(철근부식면적률) 데이터 가져오기 . 시리즈코드(시리즈명) : '" + seriesCode.getCode() + "'(" + seriesCode.getDesc()+ ")");
        List<CorrosionArea> corrosionAreas = corrosionAreaRepository.findBySeriesCode(seriesCode);
        return corrosionAreas.stream().map(corrosionArea -> modelMapper.map(corrosionArea,CorrosionAreaDto.class)).collect(Collectors.toList());

    }

}

