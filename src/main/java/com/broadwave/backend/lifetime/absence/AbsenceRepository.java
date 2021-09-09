package com.broadwave.backend.lifetime.absence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence,Long> {

    @Query("select dbTable from Absence dbTable where dbTable.ltAbsenceCode = :ltAbsenceCode")
    Optional<Absence> findByAbsenceCode(String ltAbsenceCode);

}