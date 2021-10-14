package com.broadwave.backend.ltd.dto;

import lombok.*;

/**
 * @author InSeok
 * Date : 2019-05-01
 * Time : 10:34
 * Remark : 그래프 X-Axis 라벨
  */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GraphXLabel {
    private int value;
    private String name;

}
