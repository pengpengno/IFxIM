package com.ifx.session.mapstruct;

import com.ifx.session.entity.ChatMsg;
import com.ifx.session.vo.ChatMsgVo;
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


}
