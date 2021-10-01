package com.broadwave.backend.env.tunnel;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-06-21
 * Time : 13:231
 * Remark :환경정보내 터널 자료 엔터티
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "en_tunnel")
public class Tunnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="et_tunnel_id")
    private Long id;

    @Column(name="et_road_type")
    private String roadType;       // 도로종류

    @Column(name="et_route_nm")
    private String routeName;     // 노선명

    @Column(name="et_facility_nm")
    private String facilityName;  // 시설명

    @Column(name="et_address_1")
    private String address1;  // 시도

    @Column(name="et_address_2")
    private String address2;  // 시군구

    @Column(name="et_address_3")
    private String address3;  // 읍면동

    @Column(name="et_address_4")
    private String address4;  // 리

    @Column(name="et_length")
    private Double length;  // 연장

    @Column(name="et_width")
    private Double width;  // 총폭

    @Column(name="et_effective_width")
    private Double effectiveWidth;  // 유효폭

    @Column(name="et_height")
    private Double height;  // 높이

    @Column(name="et_gongsu")
    private Double gongsu;  // 공수

    @Column(name="et_traffic")
    private Double traffic;  // 교통량

    @Column(name="et_completion_year")
    private String completionYear;  // 준공년도

    @Column(name="et_longitude")
    private Double longitude;  // 경도

    @Column(name="et_latitude")
    private Double latitude;  // 위도

    @Column(name="et_total_value")
    private String totalValue;  // 종합등급value

    @Column(name="et_total_rank")
    private String totalRank;  // 종합등급

    @Column(name="et_dong_rank")
    private String dongRank;  // 동해환경

    @Column(name="et_snow_rank")
    private String snowRank;  // 제설제

    @Column(name="et_salt_rank")
    private String saltRank;  // 염해환경

    @Column(name="et_traffic_rank")
    private String trafficRank;  // 교통량


    @Column(name="lst_modify_dt")
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id")
    private String lastModify_id;

}
