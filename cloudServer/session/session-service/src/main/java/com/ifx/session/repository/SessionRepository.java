package com.ifx.session.repository;

import com.ifx.session.entity.Session;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends ReactiveCrudRepository<Session,Long> {

}
