package com.broadwave.backend.ltd.dto;

import lombok.*;

/**
 * @author InSeok
 * Date : 2019-05-02
 * Time : 16:35
 * Remark : 그래프의값을 index 로 정렬하기위한 클래스
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GraphValue {
    private int index;
    private String value;

}
