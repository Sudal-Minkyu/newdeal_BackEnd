package com.broadwave.backend.ltd.data.ultrasonic;

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
 * Time : 14:24
 * Remark :
 */
@Service
@Slf4j
public class UltrasonicSpeedService {
    private final UltrasonicSpeedRepository ultrasonicSpeedRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UltrasonicSpeedService(UltrasonicSpeedRepository ultrasonicSpeedRepository, ModelMapper modelMapper) {
        this.ultrasonicSpeedRepository = ultrasonicSpeedRepository;
        this.modelMapper = modelMapper;
    }

    public List<UltrasonicSpeedDto> findBySeriesCode(SeriesCode seriesCode){
        log.info("측정항목(초음파속도) 데이터 가져오기 . 시리즈코드(시리즈명) : '" + seriesCode.getCode() + "'(" + seriesCode.getDesc()+ ")");
        List<UltrasonicSpeed> ultrasonicSpeeds = ultrasonicSpeedRepository.findBySeriesCode(seriesCode);
        return ultrasonicSpeeds.stream().map(ultrasonicSpeed -> modelMapper.map(ultrasonicSpeed,UltrasonicSpeedDto.class)).collect(Collectors.toList());

    }
}
