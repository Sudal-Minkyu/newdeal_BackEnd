package com.broadwave.backend.ltd.data.rebar;

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
 * Time : 17:06
 * Remark :
 */
@Service
@Slf4j
public class RebarCorrosionService {

    private final RebarCorrosionRepository rebarCorrosionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RebarCorrosionService(RebarCorrosionRepository rebarCorrosionRepository, ModelMapper modelMapper) {
        this.rebarCorrosionRepository = rebarCorrosionRepository;
        this.modelMapper = modelMapper;
    }

    public List<RebarCorrosionDto> findBySeriesCode(SeriesCode seriesCode){
        log.info("측정항목(철근부식량) 데이터 가져오기 . 시리즈코드(시리즈명) : '" + seriesCode.getCode() + "'(" + seriesCode.getDesc()+ ")");
        List<RebarCorrosion> rebarCorrosions = rebarCorrosionRepository.findBySeriesCode(seriesCode);
        return rebarCorrosions.stream().map(rebarCorrosion -> modelMapper.map(rebarCorrosion,RebarCorrosionDto.class)).collect(Collectors.toList());

    }
}
