package com.broadwave.backend.saltpermeate;

import com.broadwave.backend.saltpermeate.saltpermeateDtos.SaltPermeateListDto;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-06-13
 * Remark :
 */
public interface SaltPermeateRepositoryCustom {

    List<SaltPermeateListDto> findByStBridge(String stBridge);

}
