package com.broadwave.backend.teams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamRepositoryCustom teamRepositoryCustom;

    public Team tesmSave(Team team){
        return this.teamRepository.save(team);
    }

    public Optional<Team> findByTeamcode(String teamcode){
        return teamRepository.findByTeamcode(teamcode);
    }

    public Page<TeamDto> findAllBySearchStrings(String teamcode,String teamname, Pageable pageable){
        return teamRepositoryCustom.findAllBySearchStrings(teamcode,teamname,pageable);
    }

    public void delete(Team team){
        teamRepository.delete(team);
    }

    public List<TeamDto> findAllBySearchStringsExcel(String teamcode, String teamname) {
        return teamRepositoryCustom.findAllBySearchStringsExcel(teamcode,teamname);
    }

    // 회원가입용 소속 리스트 호출 API
    public List<TeamListDto> findByRegisterTeamList() {
        return teamRepositoryCustom.findByRegisterTeamList();
    }

}
