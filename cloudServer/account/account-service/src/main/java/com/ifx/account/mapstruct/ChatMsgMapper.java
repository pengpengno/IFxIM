package com.ifx.account.mapstruct;

import com.ifx.account.bo.ChatMsgBo;
import com.ifx.account.entity.ChatMsg;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.chat.ChatMsgRecordVo;
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
            @Mapping(source = "fromAccount.userId",target = "createUserId"),
            @Mapping(source = "sessionId",target = "sessionId")
    })
    ChatMsg tran2Msg(ChatMsgVo vo);

    @Mapping(target  = "fromAccount.account",source = "fromAccount")
    @Mapping( source =  "createTime",dateFormat = "yyyy-MM-dd HH:mm:ss",target = "msgCreateTime")
    @Mapping( source =  "id" , target = "msgId")
    ChatMsgVo tran2MsgVo(ChatMsg chatMsg);


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
                .setFromAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(chatMsgBo.getFromAccount()))
                .setToAccountInfo(ProtoBufMapper.INSTANCE.protocolAccMap(chatMsgBo.getToAccount()))
                .setContent(chatMsgBo.read())
                .setMsgId(chatMsgBo.getMsgId())
                .setSessionId(chatMsgBo.getSessionId())
                .build();
    }


    ChatMsgRecordVo chatVo2RecordVo(ChatMsgVo vo);


    ChatMsgBo chatVo2Bo(ChatMsgVo chatMsgVo);






}
