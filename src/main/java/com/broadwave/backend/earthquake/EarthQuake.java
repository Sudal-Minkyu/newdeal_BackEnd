package com.broadwave.backend.earthquake;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-06-10
 * Time :
 * Remark : 내진성능 추정서비스 교량 테이블(조한민박사)
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_eq_performance")
public class EarthQuake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eq_id")
    private Long id; // 고유ID값(NOTNULL)

    @Column(name="eq_bridge", unique = true)
    private String eqBridge; // 교량명

    @Column(name="eq_location")
    private String eqLocation; // 대상지역

    @Column(name="eq_rank")
    private String eqRank; // 내진등급

    @Column(name="eq_length")
    private String eqLength; // 주 경간장

    @Column(name="eq_configuration")
    private String eqConfiguration; // 경간 구성

    @Column(name="eq_pillar")
    private String eqPillar; // 교각기둥 구성

    @Column(name="eq_division")
    private String eqDivision; // 주형 구분

    @Column(name="eq_girder")
    private String eqGirder; // 거더형식

    @Column(name="eq_bridge_classification")
    private String eqBridgeClassification; // 교량분류

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
