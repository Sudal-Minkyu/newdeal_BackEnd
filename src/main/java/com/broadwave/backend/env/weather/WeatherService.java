package com.broadwave.backend.env.weather;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Minkyu
 * Date : 2020-11-05
 * Time :
 * Remark :
 */
@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, ModelMapper modelMapper) {
        this.weatherRepository = weatherRepository;
        this.modelMapper = modelMapper;
    }

    List<WeatherDto> findAll(){
        return weatherRepository.findAll().stream().map(tunnel -> modelMapper.map(tunnel, WeatherDto.class)).collect(Collectors.toList());
    }

}
