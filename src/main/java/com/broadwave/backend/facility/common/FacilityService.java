package com.broadwave.backend.facility.common;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Minkyu
 * Date : 2019-10-14
 * Remark :
 */
@Slf4j
@Service
public class FacilityService {

    private final ModelMapper modelMapper;
    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityService(ModelMapper modelMapper,FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
        this.modelMapper = modelMapper;
    }

    public List<FacilityDto> findAll() {
        return facilityRepository.findAll().stream().map(tunnel -> modelMapper.map(tunnel, FacilityDto.class)).collect(Collectors.toList());
    }

}
