package com.broadwave.backend.facility.common.bridge;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-10-15
 * Remark :
 */
public interface FacilityBridgeRepositoryCustom {
    List<FacilityBridgeDto> findById(Long id);
}
