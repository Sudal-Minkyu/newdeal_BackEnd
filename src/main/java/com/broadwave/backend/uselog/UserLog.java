package com.broadwave.backend.uselog;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Remark :
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nd_bs_use_log")
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="bs_use_menu_name_1")
    private String useMenu1;

    @Column(name="bs_use_menu_name_2")
    private String useMenu2;

    @Column(name = "bs_use_type")
    private String useType;

    @Column(name="bs_searchstring")
    private String searchstring;

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

}
