package com.broadwave.backend.ltd.dto;

import lombok.*;

/**
 * @author InSeok
 * Date : 2019-05-03
 * Time : 10:25
 * Remark : 그래프의값을 index 로 정렬하기위한 함수
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GraphValueString {
    private String index;
    private String value;
}
