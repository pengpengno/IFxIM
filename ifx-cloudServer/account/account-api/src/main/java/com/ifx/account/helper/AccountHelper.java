package com.ifx.account.helper;

import com.ifx.account.entity.Account;
import com.ifx.account.vo.AccountBaseInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

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
     * @param accountBaseInfo
     * @return
     */
    Account transform(AccountBaseInfo accountBaseInfo);

    /**
     * vo转po
     * @param accountBaseInfo
     * @return
     */
    @Mapping(target = "userId", ignore = true)
    Account transform4(AccountBaseInfo accountBaseInfo,@MappingTarget Account account);

    /**
     * po转vo
     * @param account
     * @return
     */
    AccountBaseInfo transform4Init(Account account);



}
