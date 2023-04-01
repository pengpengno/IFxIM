package com.ifx.account.mapstruct;

import com.ifx.account.entity.ChatMsgRecord;
import com.ifx.account.vo.chat.ChatMsgRecordVo;
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

}
