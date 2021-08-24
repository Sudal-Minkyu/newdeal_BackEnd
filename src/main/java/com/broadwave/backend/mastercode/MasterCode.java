package com.broadwave.backend.mastercode;

import com.broadwave.backend.bscodes.CodeType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Remark :
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_bs_mastercode")
public class MasterCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="nd_code_type")
    private CodeType codeType;

    @Column(name="nd_code")
    private String code;

    @Column(name="nd_name")
    private String name;

    @Column(name="nd_remark")
    private String remark;

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

    @Column(name="bc_ref_1")
    private String bcRef1;

    @Column(name="bc_ref_2")
    private String bcRef2;

    @Column(name="bc_ref_3")
    private String bcRef3;

    @Column(name="bc_ref_4")
    private String bcRef4;

}
