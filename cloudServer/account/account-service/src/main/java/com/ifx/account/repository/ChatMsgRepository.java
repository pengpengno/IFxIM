package com.ifx.account.repository;

import com.ifx.account.entity.ChatMsg;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/28
 */
@Repository
public interface ChatMsgRepository  extends ReactiveCrudRepository<ChatMsg,Long> {


}
