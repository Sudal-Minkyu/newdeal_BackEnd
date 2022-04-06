package com.broadwave.backend.safety.calculation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation,Long>, CalculationRepositoryCustom{

}