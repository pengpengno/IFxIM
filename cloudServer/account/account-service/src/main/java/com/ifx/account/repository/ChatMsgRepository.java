package com.ifx.account.repository;

import com.ifx.account.entity.ChatMsg;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/28
 */
@Repository
public interface ChatMsgRepository  extends ReactiveCrudRepository<ChatMsg,Long> {


    @Query("SELECT * FROM  chat_msg WHERE id = $1 and create_time BETWEEN $2 AND $3")
    public Flux<ChatMsg> findByIdAndCreateTimeBetween(Long id,String from,String to);
}
