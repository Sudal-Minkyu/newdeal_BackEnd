package com.broadwave.backend.mastercode;

import com.broadwave.backend.bscodes.CodeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Remark :
 */
public interface MasterCodeRepositoryCustom {
    Page<MasterCodeDto> findAllBySearchStrings(CodeType codeType, String code, String name, Pageable pageable);
}
