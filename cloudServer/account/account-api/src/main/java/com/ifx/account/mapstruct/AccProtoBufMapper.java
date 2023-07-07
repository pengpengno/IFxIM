package com.ifx.account.mapstruct;

import com.ifx.account.vo.ChatMsgVo;
import com.ifx.connect.proto.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccProtoBufMapper {

    AccProtoBufMapper INSTANCE = Mappers.getMapper(AccProtoBufMapper.class);



    default ChatMsgVo tran2ProtoChat(Chat.ChatMessage chatMessage){

        if (chatMessage == null){
            return null;
        }
        ChatMsgVo chatMsgVo = new ChatMsgVo();
        chatMsgVo.setMsgId(chatMessage.getMsgId());
        chatMsgVo.setContent(chatMessage.getContent());
        chatMsgVo.setSessionId(chatMsgVo.getSessionId());
        chatMsgVo.setFromAccount(com.ifx.connect.mapstruct.ProtoBufMapper.INSTANCE.proto2Acc(chatMessage.getFromAccountInfo()));
        chatMsgVo.setMsgSendTime(chatMsgVo.getMsgSendTime());
        chatMsgVo.setContentType(chatMsgVo.getContentType());
        chatMsgVo.setMsgCreateTime(chatMsgVo.getMsgCreateTime());
        return chatMsgVo;
    }


}
