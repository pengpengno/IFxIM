package com.ifx.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ifx.account.mapstruct.ChatMsgRecordMapper;
import com.ifx.account.repository.ChatMsgRecordRepository;
import com.ifx.account.service.ChatMsgService;
import com.ifx.account.service.IChatAction;
import com.ifx.account.service.ISessionLifeStyle;
import com.ifx.account.service.reactive.ReactiveChatMsgRecord;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.chat.ChatMsgRecordVo;
import com.ifx.common.utils.ValidatorUtil;
import com.ifx.exec.ex.bus.acc.AccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
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
    private RabbitTemplate rabbitTemplate;


    @Value("${message.chat.queue:message.chat.queue}")
    private String messageQueue;


    @Value("${message.chat.exchange:message.chat.exchange}")
    private String exchange;

    @Value("${message.action.send:message.action.send}")
    private String routingKey;

    @Autowired
    private ChatMsgRecordRepository chatMsgRecordRepository;

    @Autowired
    private ReactiveChatMsgRecord reactiveChatMsgRecord;
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
        return Mono.justOrEmpty(Optional.ofNullable(tmp))
                .doOnNext(e -> ValidatorUtil.validateThrows(chatMsgVo, ChatMsgVo.ChatPush.class)) //  验证实体合法性
                .flatMap(e -> chatMsgService.saveMsgReadPattern(e))       // 存储消息
                .flatMap(vo -> reactiveChatMsgRecord.saveByChatMsgVo(vo))  // 格式转化为消息记录
                .flatMap( record -> {
                return sessionLifeStyle.checkOnlineUserBySessionId(tmp.getSessionId()).map(e -> e.getAccount())
                        .collect(Collectors.toSet())
                        .flatMap(acc -> Mono.just(record.stream()
                        .filter(e -> CollectionUtil.contains(acc, e.getToAccount().getAccount()))
                        .collect(Collectors.toList()))) ;//  去除不在线的用户数据
            })
            .doOnNext(e-> pushMsgToMq(e))
            .then();
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



    public void pushMsgToMq(Iterable<ChatMsgRecordVo> vo){
        log.info("sent message to rabbit mq");
        Flux.fromIterable(vo)
            .map(ChatMsgRecordMapper.INSTANCE::recordVo2ChatMessage)
            .doOnNext(message-> {
                if (ObjectUtil.isNotNull(message)) {
                    Message mqMessage = new Message(message.toByteArray());
                    Mono.justOrEmpty(Optional.ofNullable(mqMessage))
                        .doOnNext(l-> rabbitTemplate.send(exchange,routingKey,mqMessage))
                        .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)).jitter(0.3d) // 重试机制
                        .filter(throwable -> throwable instanceof AmqpException)
                        .onRetryExhaustedThrow((spec, rs) -> new AccountException("remote server is invalid !")))
                        .subscribe();
                }
            }).doOnError(error -> log.info("Rabbit Message send error {} ", ExceptionUtil.stacktraceToString(error)))
            .subscribe()
            ;

    }
}
