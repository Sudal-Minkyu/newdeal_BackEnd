package com.broadwave.backend.performance.reference.weightSetting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceWeightRepository extends JpaRepository<ReferenceWeight,Long> {
    @Query("SELECT a FROM ReferenceWeight a WHERE a.id = :id")
    ReferenceWeight findByWeightSettingData(String id); // id = weight 고정
}