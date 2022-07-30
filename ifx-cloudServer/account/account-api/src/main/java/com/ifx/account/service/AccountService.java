package com.ifx.account.service;

import com.ifx.account.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wangpeng
* @description 针对表【account(基本用户信息表)】的数据库操作Service
* @createDate 2022-07-30 16:21:21
*/
public interface AccountService extends IService<Account> {
        public Boolean login();

//        public  pullFriReleation
}
