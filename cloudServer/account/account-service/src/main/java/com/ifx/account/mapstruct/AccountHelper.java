package com.ifx.account.mapstruct;

import com.ifx.account.entity.Account;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fulin
 * @Date: 2022/08/02/17:52
 * @Description:
 */
@Mapper(builder = @Builder())
public interface AccountHelper {

    AccountHelper INSTANCE = Mappers.getMapper( AccountHelper.class);

    /**
     * vo转po
     * @param accountVo
     * @return
     */
    Account transform(AccountVo accountVo);

    /**
     * vo转po
     * @param accountVo
     * @return
     */
    Account transform4(AccountVo accountVo, @MappingTarget Account account);

    Account bulidAccount(AccountVo accountVo);

    /**
     * po转vo
     * @param account
     * @return
     */
    AccountVo transform4Init(Account account);


//    @Mapping(target = )


    AccountInfo buildAccountInfo(Account acc);

    List<AccountInfo> buildAccountInfoList(List<Account> accounts);



}
