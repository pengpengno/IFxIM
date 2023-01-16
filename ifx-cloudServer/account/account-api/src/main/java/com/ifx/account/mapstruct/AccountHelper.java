package com.ifx.account.mapstruct;

import com.ifx.account.entity.Account;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
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
@Mapper
public interface AccountHelper {

    AccountHelper INSTANCE = Mappers.getMapper(AccountHelper.class);

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
