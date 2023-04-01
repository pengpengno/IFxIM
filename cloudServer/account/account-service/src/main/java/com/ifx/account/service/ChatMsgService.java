package com.ifx.account.service;

import com.ifx.account.bo.ChatMsgBo;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.chat.ChatMsgRecordVo;
import reactor.core.publisher.Flux;
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
     * @param chatMsgBo  消息实体
     */
    public Mono<Void> pushMsg(ChatMsgBo chatMsgBo);  //写扩散

    public Mono<ChatMsgVo> saveMsgReadPattern(ChatMsgVo chatMsgVo);  //读扩散存储


    /***
     * 生成 chatMsgBo
     * @return
     */
    public Flux<ChatMsgBo> generateChatMsgBo(ChatMsgVo chatMsgVo);

    /***
     * 准备需要扩扩散写的 记录
     * @param chatMsgVo
     * @return
     */
    public Flux<ChatMsgRecordVo> prepareRecordVo(ChatMsgVo chatMsgVo);




    public Mono<Long> saveMsgWritePattern(ChatMsgVo chatMsgVo);  //写扩散存储






    public Mono<Boolean>  chat(ChatMsgVo chatMsgVo);


}
