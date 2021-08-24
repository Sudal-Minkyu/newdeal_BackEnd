package com.broadwave.backend.mastercode;

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
public class MasterCodeErrDto {

    private String name;
    private String bcRef1;
    private String bcRef2;

    public String getName() {
        return name;
    }

    public String getBcRef1() {
        return bcRef1;
    }

    public String getBcRef2() {
        return bcRef2;
    }
}
