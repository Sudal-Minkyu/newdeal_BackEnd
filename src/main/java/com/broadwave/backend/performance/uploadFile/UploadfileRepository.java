package com.broadwave.backend.performance.uploadFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadfileRepository extends JpaRepository<Uploadfile,Long> {
    @Query("select a from Uploadfile a where a.piAutoNum = :autoNum and a.insert_id = :insert_id")
    Optional<Uploadfile> findByUploadFile(String autoNum, String insert_id);
}