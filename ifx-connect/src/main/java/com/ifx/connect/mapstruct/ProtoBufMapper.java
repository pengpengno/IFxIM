package com.ifx.connect.mapstruct;

import cn.hutool.core.util.StrUtil;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.proto.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

        Account.AccountInfo.Builder builder = Account.AccountInfo.newBuilder();
        String account = accountInfo.account();
        String email = accountInfo.getEmail();
        Long userId = accountInfo.userId();
        String userName = accountInfo.accountName();
        if (StrUtil.isNotBlank(email)){
            builder.setEMail(email);
        }
        if(StrUtil.isNotBlank(account)){
            builder.setAccount(account);
        }
        if (userId != null){
            builder.setUserId(userId);
        }
        if (userName != null){
            builder.setAccountName(userName);
        }
        return builder.build();

    }


    List<AccountInfo> proto2AccIterable(List<Account.AccountInfo> accountInfo);


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
