package com.broadwave.backend.teams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMapperDto {
    private Long id;
    private String teamcode;
    private String teamname;
    private String remark;
    private String mode;
}
