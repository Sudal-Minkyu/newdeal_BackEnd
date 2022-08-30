package com.broadwave.backend.costprediction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostPredictionRepository extends JpaRepository<CostPrediction,Long>, CostPredictionRepositoryCustom {

}