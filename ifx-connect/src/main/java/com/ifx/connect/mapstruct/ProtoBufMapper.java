package com.ifx.connect.mapstruct;

import com.ifx.common.base.AccountInfo;
import com.ifx.connect.proto.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/24
 */
@Mapper
public interface ProtoBufMapper {

    ProtoBufMapper INSTANCE = Mappers.getMapper(ProtoBufMapper.class);



    default Account.AccountInfo protocolAccMap(AccountInfo accountInfo){
        if (accountInfo == null){
            return null;
        }
        return Account.AccountInfo.newBuilder()
                .setEMail(accountInfo.getEmail())
                .setAccountName(accountInfo.accountName())
                .setAccount(accountInfo.getAccount())
                .setUserId(accountInfo.getUserId())
                .build();
    }

    default  AccountInfo proto2Acc(Account.AccountInfo accountInfo){
        if (accountInfo == null){
            return null;
        }
        AccountInfo res = new AccountInfo();
        res.setAccount(accountInfo.getAccount());
        res.setUserId(accountInfo.getUserId());
        res.setEmail(accountInfo.getEMail());
        res.setUserName(accountInfo.getAccountName());
        return res;
    }

}
