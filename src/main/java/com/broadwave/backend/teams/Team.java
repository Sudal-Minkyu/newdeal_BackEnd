package com.broadwave.backend.teams;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-06-17
 * Time : 15:09
 * Remark : 뉴딜 Team 클래스
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nd_bs_team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="team_id")
    private Long id;

    @Column(unique = true,name="team_code")
    private String teamcode;

    @Column(name="team_name")
    private String teamname;

    @Column(name = "remark")
    private String remark;

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
