package com.broadwave.backend.earthquake;

import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeDto;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeListDto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2022-06-10
 * Remark :
 */
public interface EarthQuakeRepositoryCustom {

    List<EarthQuakeListDto> findByEarthQuake(String eqLocation, String eqBridge);

    EarthQuakeDto findByEqBridge(String eqBridge);

}
