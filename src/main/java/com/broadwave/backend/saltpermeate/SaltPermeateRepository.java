package com.broadwave.backend.saltpermeate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaltPermeateRepository extends JpaRepository<SaltPermeate,Long>, SaltPermeateRepositoryCustom {

}