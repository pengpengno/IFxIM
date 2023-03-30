package com.ifx.account.service.impl;

import com.ifx.account.service.ChatMsgService;
import com.ifx.account.service.IChatAction;
import com.ifx.account.service.ISessionLifeStyle;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.common.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/***
 * 消息
 */
@Service
@Slf4j
public class ChatAction implements IChatAction {

    @Autowired
    private ISessionLifeStyle sessionLifeStyle;


    @Autowired
    ChatMsgService chatMsgService;


    private static final String ONLINE_USER_CONTEXT_KEY = "ONLINE";  // 用户在线状态存储key

    /***
     * <p>存储消息</p>
     * <p>获取所属会话Id
     * <p>尝试获取会话下用户状态
     * <p>根据用户状态进行消息推送
     * <p>写入消息推送日志
     * @param chatMsgVo  消息实体
     */
    @Override
    public Mono<Void> pushMsg(ChatMsgVo chatMsgVo) {
        final ChatMsgVo tmp = chatMsgVo;
        Mono<ChatMsgVo> chatMsgVoMono = Mono.justOrEmpty(Optional.ofNullable(tmp));
        chatMsgVoMono
            .doOnNext(e-> ValidatorUtil.validateThrows(chatMsgVo,ChatMsgVo.ChatPush.class)) //  验证实体合法性
            .flatMap(e->chatMsgService.saveMsgReadPattern(e)) // 存储消息
            .then(chatMsgVoMono) // 消息投递
            .map(e->tmp.getSessionId())

            .contextWrite(context -> context.put(ONLINE_USER_CONTEXT_KEY,sessionLifeStyle.checkOnlineUserBySessionId(tmp.getSessionId())))
            .map()                                                            // 开始信息投递


        ;

    }

    @Override
    public List<ChatMsgVo> pullMsg(String fromAccount, Long sessionId) {
        return null;
    }


    @Override
    public List<ChatMsgVo> pullHisMsg(Long sessionId) {
        return null;
    }


    @Override
    public List<ChatMsgVo> pullHisMsgByQuery(Long sessionId) {
        return null;
    }
}
