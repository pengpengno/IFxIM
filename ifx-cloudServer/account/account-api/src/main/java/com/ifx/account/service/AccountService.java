package com.ifx.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ifx.account.entity.Account;
import com.ifx.account.vo.AccountBaseInfo;

/**
* @author wangpeng
* @description 针对表【account(基本用户信息表)】的数据库操作Service
* @createDate 2022-07-30 16:21:21
*/
public interface AccountService extends IService<Account> {
        public Boolean login(AccountBaseInfo accountBaseInfo);
        public String register(AccountBaseInfo accountBaseInfo);

//        public  pullFriReleation
}
