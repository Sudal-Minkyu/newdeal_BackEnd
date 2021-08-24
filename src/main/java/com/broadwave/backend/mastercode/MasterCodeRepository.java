package com.broadwave.backend.mastercode;

import com.broadwave.backend.bscodes.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Remark :
 */
public interface MasterCodeRepository extends JpaRepository<MasterCode,Long> {
    Optional<MasterCode> findByAndCodeTypeAndCode(CodeType codeType, String code);

    List<MasterCode> findByAndCodeType(CodeType codeType);

    List<MasterCode> findAllByCodeTypeEqualsAndBcRef1(CodeType codeType, String emCountry);

    Optional<MasterCode> findByCode(String emCountry);

}
