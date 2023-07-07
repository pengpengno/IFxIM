package com.ifx.account.service.reactive;

import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.chat.ChatMsgRecordVo;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
public interface ReactiveChatMsgRecord {

    /**
     * 保存记录
     *
     * @param vo
     * @return
     */
    public Mono<List<ChatMsgRecordVo>> saveByChatMsgVo(ChatMsgVo vo);



}
