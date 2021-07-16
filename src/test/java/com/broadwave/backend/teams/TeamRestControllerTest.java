package com.broadwave.backend.teams;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TeamRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TeamService teamService;

    @DisplayName(" - 부서를 저장하고 저장된 부서를 삭제한다.")
    @Test
    public void team_API_reg_and_del_Test() throws Exception{

        // 부서저장 테스트
        mockMvc.perform(post("/api/team/reg")
                        .param("teamcode","Test001")
                        .param("teamname","Test이름")
                        .param("remark","Test비고")
                        .param("mode","N") // 신규저장
                )
                .andDo(print())
                .andExpect(status().isOk());

        // 저장된 부서의 코드이름을 통한 조회 테스트
        mockMvc.perform(get("/api/team/team")
                .param("teamcode","Test001")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("sendData.sendTeamData").exists())
                .andExpect(jsonPath("sendData.sendTeamData.teamcode").exists())
                .andExpect(jsonPath("sendData.sendTeamData.teamcode").value("Test001"));

        Optional<Team> optionalTeam = teamService.findByTeamcode("Test001");
        assertThat(optionalTeam.isPresent()).isEqualTo(true);

        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            assertThat(team.getTeamcode()).isEqualTo("Test001");

            // 저장 확인되면 해당 부서삭제하기
            mockMvc.perform(post("/api/team/del")
                    .param("teamcode",team.getTeamcode()))
                    .andDo(print())
                    .andExpect(status().isOk());
            //then
            Optional<Team> optionalDeleteTeam = teamService.findByTeamcode(team.getTeamcode());
            assertThat(optionalDeleteTeam.isPresent()).isEqualTo(false); // 저장된 부서의 코드가 존재하지않으면 통과
        }else{
            System.out.println("부서저장 실패");
        }

    }

}
