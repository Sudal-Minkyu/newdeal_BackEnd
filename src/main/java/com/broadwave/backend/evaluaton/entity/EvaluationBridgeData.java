package com.broadwave.backend.evaluaton.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_ev_bridge_data")
public class EvaluationBridgeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ev_id")
    private Long id; // 고유ID값

    @Column(name="ev_name")
    private String evName; // 시설명
    @Column(name="ev_road1")
    private String evRoad1; // 도로종류
    @Column(name="ev_road2")
    private String evRoad2; // 노선명
    @Column(name="ev_remark")
    private String evRemark; //비고
    @Column(name="ev_addr1")
    private String evAddr1; //시도
    @Column(name="ev_addr2")
    private String evAddr2; //시군구
    @Column(name="ev_addr3")
    private String evAddr3; //읍면동
    @Column(name="ev_addr4")
    private String evAddr4; //리

    @Column(name="ev_length")
    private Double evLength; // 총 길이
    @Column(name="ev_width")
    private Double evWidth; // 총 폭
    @Column(name="ev_width_eff")
    private Double evWidthEff; // 유효 폭
    @Column(name="ev_height")
    private Double evHeight; // 높이
    @Column(name="ev_span_cnt")
    private Integer evSpanCnt; // 경간수
    @Column(name="ev_max_span_length")
    private Double evMaxSpanLength; // 최대 경간장길이
    @Column(name="ev_superstructure")
    private String evSuperstructure; // 상부구조
    @Column(name="ev_substructure")
    private String evSubstructure; // 하부구조
    @Column(name="ev_design_loading")
    private String evDesignLoading; // 설계하중
    @Column(name="ev_traffic")
    private Integer evTraffic; // 교통량
    @Column(name="ev_org1")
    private String evOrg1; // 기관구분1
    @Column(name="ev_org2")
    private String evOrg2; // 기관구분2
    @Column(name="ev_org3")
    private String evOrg3; // 기관구분3
    @Column(name="ev_year_complete")
    private String evYearComplete; // 준공년도




}
