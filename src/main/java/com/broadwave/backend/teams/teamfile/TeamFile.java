package com.broadwave.backend.teams.teamfile;

import com.broadwave.backend.teams.Team;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-03-26
 * Time : 10:07
 * Remark : Team 클래스
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="bs_team_file_upload")
public class TeamFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_id")
    private Long id;

    @ManyToOne(targetEntity = Team.class,fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team teamId;

    @Column(name="file_address")
    private String fileAddress;

    @Column(name="file_size")
    private Long fileSize;

    @Column(name="file_insert_date")
    private LocalDateTime fileInsertDate;

}
