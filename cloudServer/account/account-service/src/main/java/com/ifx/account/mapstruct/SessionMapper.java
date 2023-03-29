package com.ifx.account.mapstruct;

import com.ifx.account.entity.Session;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.OnLineUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @description:  消息实体映射工具
 */
@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);



    /**
     * @param sessionAccount
     * @return
     */
    @Mappings(
        {
            @Mapping(source = "sessionId",target = "id"),
            @Mapping(source = "createInfo.userId",target = "createUserId")
        }
    )
    Session vo2Session(SessionInfoVo sessionAccount);


    /**
     * @return
     */
    @Mappings({
            @Mapping(source = "id",target = "sessionId"),
            @Mapping(source = "createUserId",target = "createInfo.userId")
    }
    )
    SessionInfoVo session2Vo(Session session);




    default OnLineUser.UserSearch buildSearch(Iterable<AccountInfo> accountInfos){
        if (accountInfos == null){
            return OnLineUser.UserSearch.newBuilder().build();
        }
        Iterator<AccountInfo> iterator = accountInfos.iterator();
        ArrayList<Account.AccountInfo> list = new ArrayList<>();
        if (iterator.hasNext()) {
            AccountInfo next = iterator.next();
            Account.AccountInfo accountInfo = ProtoBufMapper.INSTANCE.protocolAccMap(next);
            list.add(accountInfo);
        }
        return OnLineUser.UserSearch.newBuilder().addAllAccounts(list).build();
    }

}
