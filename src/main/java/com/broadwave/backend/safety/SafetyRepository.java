package com.broadwave.backend.safety;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafetyRepository extends JpaRepository<Safety,Long>, SafetyRepositoryCustom {

}