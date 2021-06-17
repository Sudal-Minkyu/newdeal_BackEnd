package com.broadwave.backend.teams.teamfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamFileRepository extends JpaRepository<TeamFile,Long> {

}