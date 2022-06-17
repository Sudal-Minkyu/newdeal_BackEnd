package com.broadwave.backend.earthquake;

import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeDto;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeListDto;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-06-10
 * Remark :
 */
public interface EarthQuakeRepositoryCustom {

    List<EarthQuakeListDto> findByEarthQuakeList(String eqBridge);

    EarthQuakeDto findByEqBridge(String eqBridge);

}
