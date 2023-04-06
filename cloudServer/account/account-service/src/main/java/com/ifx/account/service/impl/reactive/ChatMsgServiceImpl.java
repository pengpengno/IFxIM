package com.ifx.account.service.impl.reactive;

import com.ifx.account.bo.ChatMsgBo;
import com.ifx.account.enums.ChatMsgStatus;
import com.ifx.account.mapstruct.ChatMsgMapper;
import com.ifx.account.repository.ChatMsgRepository;
import com.ifx.account.service.ChatMsgService;
import com.ifx.account.service.ISessionAccountService;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.chat.ChatMsgRecordVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.ValidatorUtil;
import com.ifx.connect.proto.Chat;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
* @author HP
* @description 针对表【chat_msg(信息表)】的数据库操作Service实现
* @createDate 2023-01-16 16:52:17
*/
@Service
public class ChatMsgServiceImpl  implements ChatMsgService {

    @Autowired
    ChatMsgRepository chatMsgRepository;

    @Autowired
    ISessionAccountService sessionAccountService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${chat.push.queue:chat.push.queue}")
    String chatMessageQueue ;

    @Value(value = "${chat.message.exchange:chat.message.exchange}")
    String chatMessageExchange ;

    @Value(value = "${chat.message.routeKey:chat.message.push}")
    String chatMessageRouteKey ;


    @Override
    public Mono<Void> pushMsg(ChatMsgBo chatMsgBo) {
        Chat.ChatMessage chatMessage = ChatMsgMapper.INSTANCE.chatBo2ProtoChat(chatMsgBo);
        rabbitTemplate.send(chatMessageExchange,chatMessageRouteKey,new Message(chatMessage.toByteArray()));
        return Mono.empty();

    }


    @Override
    public Mono<ChatMsgVo> saveMsgReadPattern(ChatMsgVo chatMsgVo) {
        return Mono.justOrEmpty(Optional.ofNullable(chatMsgVo))
                .doOnNext(e-> ValidatorUtil.validateThrows(e, ChatMsgVo.ChatPush.class))
                .map(ChatMsgMapper.INSTANCE::tran2Msg)
                .flatMap(e-> chatMsgRepository.save(e))
                .map(entity-> ChatMsgMapper.INSTANCE.tran2MsgVo(entity))
//                .then(Mono.just(chatMsgVo))
                ;
    }


    @Override
    public Flux<ChatMsgBo> generateChatMsgBo(ChatMsgVo chatMsgVo) {
        return generateT(chatMsgVo , acc-> Mono.justOrEmpty(Optional.ofNullable(supplierChatBo(chatMsgVo,acc).get())));
    }


    private <T> Flux<T> generateT(ChatMsgVo chatMsgVo , Function<AccountInfo , Mono<T>> function){
        return Mono.justOrEmpty(Optional.of(chatMsgVo))
                .doOnNext(e->ValidatorUtil.validateThrows(e,ChatMsgVo.ChatPush.class,ChatMsgVo.ChatRecord.class))
                .flatMap(findContext-> sessionAccountService.sessionAccContextVo(findContext.getSessionId()))
                .flatMapMany(sessionAccContext -> Flux.fromIterable(sessionAccContext.getSessionAccountContext().values())) // 获取 会话下用户容器
                .flatMap(function)
                ;
    }

    public Flux<ChatMsgRecordVo> prepareRecordVo(ChatMsgVo chatMsgVo) {
        return generateT(chatMsgVo , acc-> Mono.justOrEmpty(Optional.ofNullable(supplierChatRecordVo(chatMsgVo,acc).get())));
    }

    private Supplier<ChatMsgRecordVo> supplierChatRecordVo(ChatMsgVo chatMsgVo,AccountInfo toAccountInfo){
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

    private Supplier<ChatMsgBo> supplierChatBo(ChatMsgVo chatMsgVo,AccountInfo toAccountInfo){
        return ()->{
            if (toAccountInfo == null){
                return null;
            }
            ChatMsgBo chatMsgBo = ChatMsgMapper.INSTANCE.chatVo2Bo(chatMsgVo);
            chatMsgBo.setToAccount(toAccountInfo);
            return chatMsgBo;
        };
    }

    @Override
    public Mono<Boolean> chat(ChatMsgVo chatMsgVo) {
        return null;
    }




    @Override
    public Mono<Long> saveMsgWritePattern(ChatMsgVo chatMsgVo) {
        return null;
    }
}




