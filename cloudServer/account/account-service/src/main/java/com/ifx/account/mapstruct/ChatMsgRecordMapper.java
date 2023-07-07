package com.ifx.account.mapstruct;

import com.ifx.account.entity.ChatMsgRe;
import com.ifx.account.enums.ChatMsgStatus;
import com.ifx.account.enums.ContentType;
import com.ifx.account.vo.chat.ChatMsgRecordVo;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
@Mapper
public interface ChatMsgRecordMapper {

    ChatMsgRecordMapper INSTANCE = Mappers.getMapper(ChatMsgRecordMapper.class);

    @Mapping(source = "toAccount.userId" ,target = "toUserId")
    ChatMsgRe chatVo2Record(ChatMsgRecordVo vo);


    ChatMsgRecordVo record2Vo(ChatMsgRe record);



    default Chat.ChatMessage recordVo2ChatMessage(ChatMsgRecordVo vo){
        if (vo == null){
            return null;
        }
        ContentType contentType = vo.getContentType();
        ChatMsgStatus status = vo.getStatus();
        Chat.ChatMessage.Builder builder = Chat.ChatMessage.newBuilder();
        Long msgId = vo.getMsgId();
        String content = vo.getContent();
        Long sessionId = vo.getSessionId();
        if (msgId !=null){
            builder.setMsgId(msgId);
        }
        if (content !=null){
            builder.setContent(content);
        }
        if (sessionId!=null){
            builder.setSessionId(sessionId);
        }
        if(contentType != null){
            builder.setType( contentType.getCharMessageType());
        }

        if (status != null){
            builder.setMessagesStatus(status.getStatus());
        }
        return builder
                .setFromAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(vo.getFromAccount()))
                .setToAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(vo.getToAccount()))
                .build();
    }


    Iterable<Chat.ChatMessage> recordVo2ChatMessage(Iterable<ChatMsgRecordVo> vos);

}
