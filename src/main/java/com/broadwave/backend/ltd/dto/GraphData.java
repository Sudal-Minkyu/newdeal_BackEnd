package com.broadwave.backend.ltd.dto;

import lombok.*;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-05-02
 * Time : 16:08
 * Remark : 그래프용 데이터
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GraphData {
    private String dataTitle;
    private List<String> inspectData;

}
