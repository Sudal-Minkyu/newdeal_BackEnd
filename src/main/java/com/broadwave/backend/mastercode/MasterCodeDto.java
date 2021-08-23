package com.broadwave.backend.mastercode;

import com.broadwave.backend.bscodes.CodeType;
import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Remark :
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class MasterCodeDto {
    private Long id;
    private CodeType codeType;
    private String code;
    private String name;
    private String remark;

    public Long getId() {
        return id;
    }

    public String getCodeType() {
        return codeType.getDesc();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

}
