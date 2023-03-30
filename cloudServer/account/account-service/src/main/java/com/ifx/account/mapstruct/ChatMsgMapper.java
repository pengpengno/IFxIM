package com.ifx.account.mapstruct;

import com.ifx.account.bo.ChatMsgBo;
import com.ifx.account.entity.ChatMsg;
import com.ifx.account.enums.ChatMsgStatus;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @description:  消息实体映射工具
 */
@Mapper
public interface ChatMsgMapper {

    ChatMsgMapper INSTANCE = Mappers.getMapper(ChatMsgMapper.class);

    @Mappings({
            @Mapping(source = "fromAccount.account",target = "fromAccount"),
            @Mapping(source = "sessionId",target = "toSessionId")
    })
    ChatMsg tran2Msg(ChatMsgVo vo);


    default Chat.ChatMessage tran2ProtoMsg(ChatMsgVo chatMsgVo){
        if (chatMsgVo == null){
            return null;
        }
        AccountInfo fromAccount = chatMsgVo.getFromAccount();
        return Chat.ChatMessage.newBuilder()
                .setFromAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(fromAccount))
                .setContent(chatMsgVo.getContent())
                .build();
    }

    default Chat.ChatMessage chatBo2ProtoChat(ChatMsgBo chatMsgBo){
        if (chatMsgBo == null){
            return null;
        }
        return Chat.ChatMessage.newBuilder()
                .setFromAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(chatMsgBo.getFromAccountInfo()))
                .setToAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(chatMsgBo.getToAccountInfo()))
                .setContent(chatMsgBo.read())
                .setMsgId(chatMsgBo.getMsgId())
                .setSessionId(chatMsgBo.getSessionId())
                .build();
    }

    default ChatMsg unSentMsg(ChatMsgVo vo){
        ChatMsg chatMsg = tran2Msg(vo);
        chatMsg.setStatus(ChatMsgStatus.UNSENT.name());
        return chatMsg;
    }




}
