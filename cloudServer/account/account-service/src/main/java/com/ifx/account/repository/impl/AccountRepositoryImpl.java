package com.ifx.account.repository.impl;

import com.ifx.account.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/14
 */

@Repository
public class AccountRepositoryImpl  {
    @Autowired
    private DatabaseClient databaseClient;
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;
    public Mono<Account> findByAccount(String account) {
        return  r2dbcEntityTemplate
                .selectOne(Query.query(Criteria.where("account").is(account)), Account.class);
    }



}
