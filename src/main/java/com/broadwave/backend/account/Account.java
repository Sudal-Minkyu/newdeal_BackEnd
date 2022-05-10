package com.broadwave.backend.account;

import com.broadwave.backend.account.AccountDtos.AccountRole;
import com.broadwave.backend.teams.Team;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-06-17
 * Time : 13:21
 * Remark : 뉴딜 사용자계정 테이블
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_bs_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(unique = true,name="user_id")
    private String userid;

    @Column(name="user_name")
    private String username;

    @Column(name="user_password")
    private String password;

    @Column(name="user_email")
    private String email;

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

    @Enumerated(EnumType.STRING)
    @Column(name="user_role")
    private AccountRole role;

    @ManyToOne(targetEntity = Team.class,fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;


}
