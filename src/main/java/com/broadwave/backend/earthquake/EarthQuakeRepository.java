package com.broadwave.backend.earthquake;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EarthQuakeRepository extends JpaRepository<EarthQuake,Long>, EarthQuakeRepositoryCustom {

}