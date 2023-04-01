package com.ifx.account.repository;

import com.ifx.account.entity.ChatMsgRecord;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
public interface ChatMsgRecordRepository extends ReactiveCrudRepository<ChatMsgRecord,Long> {
}
