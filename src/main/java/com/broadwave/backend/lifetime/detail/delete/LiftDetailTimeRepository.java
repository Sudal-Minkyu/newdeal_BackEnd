package com.broadwave.backend.lifetime.detail.delete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiftDetailTimeRepository extends JpaRepository<LifeDetailTime,Long> {

}