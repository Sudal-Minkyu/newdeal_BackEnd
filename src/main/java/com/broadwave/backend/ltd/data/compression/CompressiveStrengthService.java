package com.broadwave.backend.ltd.data.compression;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author InSeok
 * Date : 2019-04-30
 * Time : 13:40
 * Remark : 압축강도에대한 그래프용 데이터가공을위한 서비스
 */
@Slf4j
@Service
public class CompressiveStrengthService {

    private final CompressiveStrengthRepository compressiveStrengthRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CompressiveStrengthService(CompressiveStrengthRepository compressiveStrengthRepository, ModelMapper modelMapper) {
        this.compressiveStrengthRepository = compressiveStrengthRepository;
        this.modelMapper = modelMapper;
    }

    public List<CompressiveStrengthDto> findBySeriesCode(SeriesCode seriesCode){
        log.info("측정항목(압축강도) 데이터 가져오기 . 시리즈코드(시리즈명) : '" + seriesCode.getCode() + "'(" + seriesCode.getDesc()+ ")");
        List<CompressiveStrength> strengths = compressiveStrengthRepository.findBySeriesCode(seriesCode);
        return strengths.stream().map(strength -> modelMapper.map(strength,CompressiveStrengthDto.class)).collect(Collectors.toList());

    }
}
