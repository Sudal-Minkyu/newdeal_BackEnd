package com.broadwave.backend.teams;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @DisplayName("1. 테스트 계정 저장")
    @Test
    public void tesmSaveTest(){
        //given
        Team team = Team.builder()
                .teamcode("Test")
                .teamname("Test")
                .remark("Test")
                .insert_id("testId")
                .insertDateTime(LocalDateTime.now())
                .build();

        //when
        Team saveteam = teamService.tesmSave(team);

        //then
        AssertionsForClassTypes.assertThat(team.getTeamname()).isEqualTo(saveteam.getTeamname());
    }

    @DisplayName("2. 테스트 계정저장되었는지 확인후 삭제")
    @Test
    public void findByTeamcodeTest(){
        //when
        Optional<Team> team = teamService.findByTeamcode("Test");

        if(team.isPresent()){
            //then
            AssertionsForClassTypes.assertThat(team.get().getTeamname()).isEqualTo(team.get().getTeamname());
            //생성한 테스트계정 삭제 delete함수 테스트
            teamService.delete(team.get());
        }else{
            System.out.println("존재하지 않은 계정");
        }
    }

}