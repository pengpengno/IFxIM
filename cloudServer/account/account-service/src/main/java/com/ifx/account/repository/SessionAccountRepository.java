package com.ifx.account.repository;


import com.ifx.account.entity.SessionAccount;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SessionAccountRepository extends ReactiveCrudRepository<SessionAccount,Long> {

    @Query("select * from  session_account where session_id = :sessionId" )
    Flux<SessionAccount> queryBySessionId(@Param("sessionId") Long sessionId);





}
