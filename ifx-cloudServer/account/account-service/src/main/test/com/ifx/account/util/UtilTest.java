package com.ifx.account.util;

import com.ifx.account.entity.Account;
import com.ifx.account.mapstruct.AccountHelper;
import com.ifx.common.base.AccountInfo;
import org.junit.Assert;
import org.junit.Test;

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
        accountInfo = AccountHelper.INSTANCE.buildAccountInfo(account );
        //then
        Assert.assertEquals(accountInfo.getAccount(),account.getAccount());
    }
}
