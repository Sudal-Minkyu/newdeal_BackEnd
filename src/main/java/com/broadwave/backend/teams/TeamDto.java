package com.broadwave.backend.teams;

import com.broadwave.backend.excel.DtoExcel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto implements DtoExcel {

    private Long id;
    private String teamcode;
    private String teamname;
    private String remark;

    @Override
    public List<String> toArray() {
        return Arrays.asList(this.teamcode, this.teamname,this.remark);
    }

}
