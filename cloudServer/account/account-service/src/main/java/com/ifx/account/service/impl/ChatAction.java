package com.ifx.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.entity.ChatMsgRecord;
import com.ifx.account.mapstruct.ChatMsgRecordMapper;
import com.ifx.account.repository.ChatMsgRecordRepository;
import com.ifx.account.service.ChatMsgService;
import com.ifx.account.service.IChatAction;
import com.ifx.account.service.ISessionLifeStyle;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/***
 * 消息
 */
@Service
@Slf4j
public class ChatAction implements IChatAction {

    @Autowired
    private ISessionLifeStyle sessionLifeStyle;


    @Autowired
    private ChatMsgRecordRepository chatMsgRecordRepository;
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
            .flatMap(e-> chatMsgService.saveMsgReadPattern(e)) // 存储消息
            .flatMapMany(vo -> chatMsgService.prepareRecordVo(vo))
            .collect(Collectors.toList())
            .doOnNext( vo -> {
                List<ChatMsgRecord> list = vo.stream().map(ChatMsgRecordMapper.INSTANCE::chatVo2Record).collect(Collectors.toList());
                chatMsgRecordRepository.saveAll(list);
            })
            .flatMap( record -> {
                return Mono.deferContextual(contextView -> {
                    List<AccountInfo>  flux = (List<AccountInfo>)contextView.get(ONLINE_USER_CONTEXT_KEY);
                    Set<String> accountSet = flux.stream().map(e -> e.getAccount()).collect(Collectors.toSet());
                    return record.stream().filter(e-> CollectionUtil.contains(accountSet,e.getToAccount().getAccount())).collect(Collectors.toList());
                });
            })
            .contextWrite(context -> context.put(ONLINE_USER_CONTEXT_KEY,sessionLifeStyle.checkOnlineUserListBySessionId(tmp.getSessionId())))    // 开始信息投递
        ;
        return null;
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
