package com.broadwave.backend.evaluaton;

import com.broadwave.backend.evaluaton.entity.EvaluationBridgeData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationBridgeDataRepository extends JpaRepository<EvaluationBridgeData,Long> ,EvaluationBridgeDataRepositoryCustom{

}
