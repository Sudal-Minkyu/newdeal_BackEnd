package com.broadwave.backend.lifetime.detail.carbonation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CabonationRepository extends JpaRepository<Cabonation,Long>, CabonationRepositoryCustom {
    @Query("select a from Cabonation a where a.ltDetailAutoNum = :ltDetailAutoNum")
    Optional<Cabonation> findByLtDetailAutoNum(String ltDetailAutoNum);
}