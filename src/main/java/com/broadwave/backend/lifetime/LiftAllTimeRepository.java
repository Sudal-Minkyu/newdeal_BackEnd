package com.broadwave.backend.lifetime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiftAllTimeRepository extends JpaRepository<LifeAllTime,Long> {

}