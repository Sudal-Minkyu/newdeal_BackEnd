package com.broadwave.backend.lifetime.detail.cabonationThreePlate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CabonationThreePlateRepository extends JpaRepository<CabonationThreePlate,Long>, CabonationThreePlateRepositoryCustom {
    @Query("select a from CabonationThreePlate a where a.ltDetailAutoNum = :ltDetailAutoNum")
    Optional<CabonationThreePlate> findByLtDetailAutoNum(String ltDetailAutoNum);
}