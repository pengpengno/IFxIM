package com.ifx.account.util;

import com.ifx.account.entity.Account;
import com.ifx.account.mapstruct.AccountMapper;
import com.ifx.common.base.AccountInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/16
 */
public class UtilTest {
    @Test
    public void testMapperInfo(){
        //given
        Account account = new Account(  );
        AccountInfo accountInfo = AccountInfo.builder().build();
        account.setAccount("sadsadas");
        //when
        accountInfo = AccountMapper.INSTANCE.buildAccountInfo(account );
        //then
        Assertions.assertEquals(accountInfo.getAccount(),account.getAccount());
    }
}
