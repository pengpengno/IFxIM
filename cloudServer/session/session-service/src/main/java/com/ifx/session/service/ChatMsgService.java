package com.ifx.session.service;

import com.ifx.session.vo.ChatMsgVo;
import reactor.core.publisher.Mono;

/**
 *
* @author HP
* @description 针对表【chat_msg(信息表)】的数据库操作Service
* @createDate 2023-01-16 16:52:17
*/
public interface ChatMsgService  {



    /**
     * <p>推送消息</p>
     * @param chatMsgVo  消息实体
     */
    public void pushMsg(ChatMsgVo chatMsgVo);  //写扩散


    public Mono<Boolean>  chat(ChatMsgVo chatMsgVo);


}
