package com.broadwave.backend.mastercode;

import com.broadwave.backend.bscodes.CodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Remark :
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterCodeMapperDto {
    private CodeType codeType;
    private String code;
    private String name;
    private String remark;
    private String bcRef1;
    private String bcRef2;
    private String bcRef3;
    private String bcRef4;
}
