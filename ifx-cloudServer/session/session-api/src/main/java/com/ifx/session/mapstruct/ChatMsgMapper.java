package com.ifx.session.mapstruct;

import com.ifx.account.entity.Account;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.session.entity.ChatMsg;
import com.ifx.session.vo.ChatMsgVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 *
 * @description:  消息实体映射工具
 */
@Mapper
public interface ChatMsgMapper {

    ChatMsgMapper INSTANCE = Mappers.getMapper(ChatMsgMapper.class);

    /**
     * vo转po
     * @param chatMsgVo
     * @return
     */
    ChatMsg transform(ChatMsgVo chatMsgVo);

    /**
     * vo转po
     * @param accountVo
     * @return
     */
    Account transform4(AccountVo accountVo, @MappingTarget Account account);

    Account transform4(AccountVo accountVo);

    /**
     * po转vo
     * @param account
     * @return
     */
    AccountVo transform4Init(Account account);


//    @Mapping(target = )
    AccountInfo trans2Info(Account account);


    List<AccountInfo> trans2Info(List<Account> accounts);



}
