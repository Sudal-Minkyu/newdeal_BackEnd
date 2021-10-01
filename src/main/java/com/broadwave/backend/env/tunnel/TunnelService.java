package com.broadwave.backend.env.tunnel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author InSeok
 * Date : 2019-06-21
 * Time : 15:16
 * Remark :
 */
@Service
public class TunnelService {
    private final TunnelRepository tunnelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TunnelService(TunnelRepository tunnelRepository, ModelMapper modelMapper) {
        this.tunnelRepository = tunnelRepository;
        this.modelMapper = modelMapper;
    }

    List<TunnelDto> findAll(){
        return tunnelRepository.findAll().stream().map(tunnel -> modelMapper.map(tunnel, TunnelDto.class)).collect(Collectors.toList());


    }

    Optional<Tunnel> findByid(Long id){
        return tunnelRepository.findById(id);
    }

    List<Tunnel> findByFacilityNameContaining(String facilityName){
        return tunnelRepository.findByFacilityNameContaining(facilityName);
    }
}
