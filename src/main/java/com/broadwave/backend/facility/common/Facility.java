package com.broadwave.backend.facility.common;

import com.broadwave.backend.bscodes.DonghaeType;
import com.broadwave.backend.bscodes.FlyingSaltType;
import com.broadwave.backend.bscodes.HeavyVehicleType;
import com.broadwave.backend.bscodes.SnowRemoverType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu //수정
 * Date : 2019-10-14
 * Remark :  시설물공통정보 관리 엔터티
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fa_psc_list")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fa_psc_id")
    private Long id; // ID

    @Column(name="fa_psc_name")
    private String faPscName; // 시설물이름

    @Column(name="fa_psc_city")
    private String faPscCity; // 행정구역

    @Column(name="fa_psc_address")
    private String faPscAddress; // 주소

    @Column(name="fa_psc_continuation")
    private Double faPscContinuation; // 시설물연장 (단위:m)

    @Column(name="fa_psc_longitude")
    private Double faPscLongitude;  // 경도

    @Column(name="fa_psc_latitude")
    private Double faPscLatitude; // 위도

    @Column(name="fa_psc_startpoint")
    private Double faPscStartpoint;  // 좌표(시점)

    @Column(name="fa_psc_endpoint")
    private Double faPscEndpoint; // 좌표(종점)

    @Column(name="fa_psc_completion")
    private Integer faPscCompletion; // 준공년도

    @Enumerated(EnumType.STRING)
    @Column(name="fa_psc_salt")
    private FlyingSaltType faPscSalt; // 비래염분

    @Enumerated(EnumType.STRING)
    @Column(name="fa_psc_snow")
    private SnowRemoverType faPscSnow; //제설제

    @Enumerated(EnumType.STRING)
    @Column(name="fa_psc_eastsea")
    private DonghaeType faPscEastsea; // 동해환경

    @Enumerated(EnumType.STRING)
    @Column(name="fa_psc_vehicle")
    private HeavyVehicleType faPscVehicle; // 중차량환경

    @Column(name="lst_modify_dt") // 생성시간날짜
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id") //생성한자의 아이디
    private String lastModify_id;

}
