package com.broadwave.backend.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByUsername(String username);

    @Query("select a from Account a join fetch a.team where a.userid = :userid")
    Optional<Account> findByUserid(@Param("userid") String userid);

//    @Query("select count(bs_account) from Account bs_account")
//    void findByAccountCount();

}