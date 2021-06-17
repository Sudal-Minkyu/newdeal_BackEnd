package com.broadwave.backend.teams;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamRepositoryCustom {
    Page<TeamDto> findAllBySearchStrings(String teamcode, String teamname, Pageable pageable);
    List<TeamDto> findAllBySearchStringsExcel(String teamcode, String teamname);
}
