package com.broadwave.backend.facility.common.bridge;

import com.broadwave.backend.facility.common.Facility;
import lombok.*;

import javax.persistence.*;

/**
 * @author Minkyu
 * Date : 2019-10-14
 * Remark :  교량환경 데이터
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fa_psc_bridge")
public class FacilityBridge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fa_psc_bridge_id")
    private Long id;

    @ManyToOne(targetEntity = Facility.class,fetch = FetchType.LAZY)
    @JoinColumn(name="fa_psc_id")
    private Facility facility;

    @Column(name="fa_psc_bridge_number")
    private Long faPscBridgeNumber; // 교량 경간 수

//    @Enumerated(EnumType.STRING)
    @Column(name="fa_psc_bridge_type")
    private String faPscBridgeType; // 교량 형식

    @Column(name="fa_psc_bridge_width")
    private Double faPscBridgeWidth; // 교량 폭

    @Column(name="fa_psc_bridge_maxspanlength")
    private Double faPscBridgeMaxspanlength; // 최대 경간장

//    @Enumerated(EnumType.STRING)
    @Column(name="fa_psc_bridge_step")
    private String faPscBridgeBridgeStep; // 설계하중

    @Column(name="fa_psc_bridge_height")
    private Double faPscBridgeHeight; // 교량 높이

    @Column(name="fa_psc_bridge_seaheight")
    private Double faPscBridgeSeaheight; // 교면해발높이

    @Column(name="fa_psc_bridge_slab_thickness")
    private Double faPscBridgeSlabThickness; // 슬래브 두께

    @Column(name="fa_psc_bridge_slab_width")
    private Double faPscBridgeSlabWidth; // 슬래브 폭

    @Column(name="fa_psc_bridge_pier_type")
    private String faPscBridgePierType; // 교각 형식

    @Column(name="fa_psc_bridge_pier_number")
    private Long faPscBridgePierNumber; // 교각 수

    @Column(name="fa_psc_bridge_pier_support_number")
    private Long faPscBridgePierSupportNumber; // 교량 받침 수

    @Column(name="fa_psc_bridge_expansionjoint_number")
    private Long faPscBridgeExpansionjointNumber; //신축이음 개소 수

}
