package com.broadwave.backend.earthquake.EarthQuakeDtos;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-06-10
 * Time :
 * Remark : 내진성능 추정서비스 ListDto
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class EarthQuakeListDto {

    private String eqBridge; // 교량명
    private String eqLocation; // 대상지역
    private String eqRank; // 내진등급
    private String eqLength; // 주 경간장

}
