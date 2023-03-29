package com.ifx.account.mapstruct;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.entity.Session;
import com.ifx.account.entity.SessionAccount;
import com.ifx.account.vo.session.SessionAccountVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.common.base.AccountInfo;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/17
 */
public interface SessionAccMap {


    SessionAccMap INSTANCE = Mappers.getMapper(SessionAccMap.class);

    /**
     * @param sessionAccount
     * @return
     */
    @Mappings({
            @Mapping(source = "createInfo.account",target = "account"),
            @Mapping(source = "createInfo.account",target = "createAccount"),
    })
    SessionAccount transform(SessionAccountVo sessionAccount);


    /**
     * @param sessionAccount
     * @return
     */
    @Mappings(
            @Mapping(source = "group",target = "sessionGroup")
    )
    Session transform(SessionInfoVo sessionAccount);


    default SessionAccountVo transSessionAccount(SessionInfoVo sessionInfoVo ,Iterable<SessionAccount> sessionAccounts, AccountInfo accountInfo){
        SessionAccountVo sessionAccountVo = new SessionAccountVo();
        sessionAccountVo.setCreateInfo(accountInfo);
        sessionAccountVo.setSessionId(sessionInfoVo.getSessionId());
        sessionAccountVo.setSessionName(sessionAccountVo.getSessionName());
        Set<Long> idSet = CollectionUtil.newHashSet();
        sessionAccounts.forEach(e->idSet.add(e.getUserId()));
        sessionAccountVo.setAddUseIdSet(idSet);
        return sessionAccountVo;
    }
}
