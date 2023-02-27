package com.ifx.session.mapstruct;

import com.ifx.session.entity.Session;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.vo.session.SessionAccountVo;
import com.ifx.session.vo.session.SessionInfoVo;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

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
            @Mapping(source = "createInfo.account",target = "accountId"),
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

}
