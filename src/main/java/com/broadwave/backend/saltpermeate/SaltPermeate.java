package com.broadwave.backend.saltpermeate;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-06-13
 * Time :
 * Remark : 열화환경별 염화물 테이블(김건수박사)
 */
@Entity
@Data
@EqualsAndHashCode(of = "stId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_st_permeate")
public class SaltPermeate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="st_id")
    private Long stId; // 고유ID값(NOTNULL)

    @Column(name="st_bridge", unique = true)
    private String stBridge; // 교량명

    @Column(name="st_location_1")
    private String stLocation1; // 소재지 1

    @Column(name="st_location_2")
    private String stLocation2; // 소재지 2

    @Column(name="st_coordinate_x")
    private String stCoordinateX; // 좌표 X

    @Column(name="st_coordinate_y")
    private String stCoordinateY; // 좌표 Y

    @Column(name="st_freeze")
    private String stFreeze; // 동결융해

    @Column(name="st_snow")
    private String stSnow; // 제설제

    @Column(name="st_salt")
    private String stSalt; // 비래염분

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

}
