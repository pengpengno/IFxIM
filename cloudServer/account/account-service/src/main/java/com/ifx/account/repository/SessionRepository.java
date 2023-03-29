package com.ifx.account.repository;

import com.ifx.account.entity.Session;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends ReactiveCrudRepository<Session,Long> {

}
