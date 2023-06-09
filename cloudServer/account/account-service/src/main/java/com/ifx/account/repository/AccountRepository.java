package com.ifx.account.repository;

import com.ifx.account.entity.Account;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/14
 */
@Repository
//public interface AccountRepository  extends ReactiveCrudRepository<Account,Long> , QuerydslPredicateExecutor<Account> {
public interface AccountRepository  extends ReactiveCrudRepository<Account,Long>  {

    @Query("SELECT  * FROM account WHERE account = :account")
    Mono<Account> findByAccount(@Param("account") String account);

    @Query("SELECT  * FROM account WHERE id in (:userId)")
    Flux<Account> findByAccountIdIn(@Param("userId") Set<Long> userId);




}
