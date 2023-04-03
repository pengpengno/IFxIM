package com.ifx.account.mapstruct;

import com.ifx.account.entity.ChatMsgRecord;
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
    ChatMsgRecord chatVo2Record(ChatMsgRecordVo vo);


    ChatMsgRecordVo record2Vo(ChatMsgRecord record);



    default Chat.ChatMessage recordVo2ChatMessage(ChatMsgRecordVo vo){
        if (vo == null){
            return null;
        }
        ContentType contentType = vo.getContentType();
        ChatMsgStatus status = vo.getStatus();
        return Chat.ChatMessage.newBuilder()
                .setMsgId(vo.getMsgId())
                .setContent(vo.getContent())
                .setFromAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(vo.getFromAccount()))
                .setToAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(vo.getToAccount()))
                .setSessionId(vo.getSessionId())
                .setType(contentType == null ? null :contentType.getCharMessageType())
                .setMessagesStatus(status == null ? null :status.getStatus() )
                .build();
    }


    Iterable<Chat.ChatMessage> recordVo2ChatMessage(Iterable<ChatMsgRecordVo> vos);

}
