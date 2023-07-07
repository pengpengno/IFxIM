package com.ifx.account.repository;

import com.ifx.account.entity.ChatMsgRe;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMsgRecordRepository extends ReactiveCrudRepository<ChatMsgRe,Long> {
}
