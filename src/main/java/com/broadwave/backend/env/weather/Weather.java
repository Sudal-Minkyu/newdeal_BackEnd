package com.broadwave.backend.env.weather;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2020-11-05
 * Time :
 * Remark :환경정보내 기상관측장비 자료 엔터티
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "en_weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ew_id")
    private Long id;

    @Column(name="ew_name")
    private String ewName; // 지점명

    @Column(name="ew_latitude")
    private Double ewLatitude; // 위도

    @Column(name="ew_longitude")
    private Double ewLongitude; // 경도

    @Column(name="ew_number")
    private String ewNumber; // 지점번호

    @Column(name="ew_altitude")
    private Double ewAltitude; // 해발고도

    @Column(name="ew_address")
    private String ewAddress; // 주소

    @Column(name="insert_date")
    private LocalDateTime insert_date;

    @Column(name="insert_id")
    private String insert_id;

}
