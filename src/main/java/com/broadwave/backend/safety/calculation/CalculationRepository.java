package com.broadwave.backend.safety.calculation;

import com.broadwave.backend.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation,Long>, CalculationRepositoryCustom{

    @Transactional
    @Modifying
    @Query("delete from Calculation a where a.id in :deleteList")
    void calculationDelete(List<Long> deleteList);

    @Query("select dbTable from Calculation dbTable where dbTable.sfId.id = :id")
    List<Calculation> findBySfId(Long id);

}