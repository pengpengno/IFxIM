package com.ifx.account.mapstruct;

import com.ifx.account.entity.ChatMsg;
import com.ifx.account.enums.ChatMsgStatus;
import com.ifx.account.vo.ChatMsgVo;
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


    default ChatMsg unSentMsg(ChatMsgVo vo){
        ChatMsg chatMsg = tran2Msg(vo);
        chatMsg.setStatus(ChatMsgStatus.UNSENT.name());
        return chatMsg;
    }

}
