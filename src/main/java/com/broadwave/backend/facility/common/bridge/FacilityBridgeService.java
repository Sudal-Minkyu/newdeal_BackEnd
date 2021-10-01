package com.broadwave.backend.facility.common.bridge;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2019-10-14
 * Remark :
 */
@Slf4j
@Service
public class FacilityBridgeService {

    private final ModelMapper modelMapper;
    private final FacilityBridgeRepository facilityBridgeRepository;

    @Autowired
    public FacilityBridgeService(ModelMapper modelMapper,
                                 FacilityBridgeRepository facilityBridgeRepository) {
        this.modelMapper = modelMapper;
        this.facilityBridgeRepository = facilityBridgeRepository;
    }

    public FacilityBridge save(FacilityBridge facilityBridge){
        return facilityBridgeRepository.save(facilityBridge);
    }

    public Optional<FacilityBridge> findByFacilityBridgeId(Long facility) {
        return facilityBridgeRepository.findById(facility);
    }

    public FacilityBridgeDto findByInfo(Long id) {
        Optional<FacilityBridge> facilityBridge = facilityBridgeRepository.findById(id);
        return facilityBridge.map(generalInfo -> modelMapper.map(generalInfo, FacilityBridgeDto.class)).orElse(null);
    }
}
