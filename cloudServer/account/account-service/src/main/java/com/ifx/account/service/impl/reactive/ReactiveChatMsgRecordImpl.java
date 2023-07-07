package com.ifx.account.service.impl.reactive;

import com.ifx.account.enums.ChatMsgStatus;
import com.ifx.account.mapstruct.ChatMsgMapper;
import com.ifx.account.mapstruct.ChatMsgRecordMapper;
import com.ifx.account.repository.ChatMsgRecordRepository;
import com.ifx.account.service.ISessionAccountService;
import com.ifx.account.service.reactive.ReactiveChatMsgRecord;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.chat.ChatMsgRecordVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
@Service
@Slf4j
public class ReactiveChatMsgRecordImpl implements ReactiveChatMsgRecord {

    @Autowired
    ChatMsgRecordRepository chatMsgRecordRepository;


    @Autowired
    ISessionAccountService sessionAccountService;
    @Override
    public Mono<List<ChatMsgRecordVo>> saveByChatMsgVo(ChatMsgVo chatMsgVo) {
        Mono<List<ChatMsgRecordVo>> share = Mono.justOrEmpty(Optional.ofNullable(chatMsgVo))
                .flatMapMany(vo -> prepareRecordVo(vo))
                .collectList()
                .share();
        return  share
                .map(vo -> {
                    return vo.stream()
                        .map(ChatMsgRecordMapper.INSTANCE::chatVo2Record)
                        .collect(Collectors.toList());// 存储消息至记录表
                })
                .flatMapIterable(e->e)
                .flatMap( vo ->  {
                    return chatMsgRecordRepository.save(vo);
                })
                .then(share)
                .log()
                ;

    }


    private <T> Flux<T> generateT(ChatMsgVo chatMsgVo , Function<AccountInfo, Mono<T>> function){
        return Mono.justOrEmpty(Optional.of(chatMsgVo))
                .doOnNext(e-> ValidatorUtil.validateThrows(e,ChatMsgVo.ChatPush.class,ChatMsgVo.ChatRecord.class))
                .flatMap(findContext-> sessionAccountService.sessionAccContextVo(findContext.getSessionId()))
                .flatMapMany(sessionAccContext -> Flux.fromIterable(sessionAccContext.getSessionAccountContext().values())) // 获取 会话下用户容器
                .flatMap(function)
                ;
    }

    public Flux<ChatMsgRecordVo> prepareRecordVo(ChatMsgVo chatMsgVo) {

        return generateT(chatMsgVo , acc-> Mono.justOrEmpty(Optional.ofNullable(supplierChatRecordVo(chatMsgVo,acc).get())))
                ;
    }

    private Supplier<ChatMsgRecordVo> supplierChatRecordVo(ChatMsgVo chatMsgVo, AccountInfo toAccountInfo){
        return ()-> {
            if (toAccountInfo == null){
                return null;
            }
            ChatMsgRecordVo vo = ChatMsgMapper.INSTANCE.chatVo2RecordVo(chatMsgVo);
            vo.setToAccount(toAccountInfo);
            vo.setStatus(ChatMsgStatus.UNSENT);
            return vo;
        };
    }
}
