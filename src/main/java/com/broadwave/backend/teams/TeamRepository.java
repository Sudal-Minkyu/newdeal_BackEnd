package com.broadwave.backend.teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long>, QuerydslPredicateExecutor<Team> {
    Optional<Team> findByTeamcode(String teamcode);
}
