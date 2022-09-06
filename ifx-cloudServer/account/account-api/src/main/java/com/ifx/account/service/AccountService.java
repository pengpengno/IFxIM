package com.ifx.account.service;

import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.common.base.AccountInfo;

import java.util.List;

/**
* @author wangpeng
* @description 针对表【account(基本用户信息表)】的数据库操作Service
* @createDate 2022-07-30 16:21:21
*/
public interface AccountService  {

        public Boolean login(AccountBaseInfo accountBaseInfo);

        public AccountInfo loginAndGetCur(AccountBaseInfo accountBaseInfo) ;


        public String register(AccountBaseInfo accountBaseInfo);

        public List<AccountBaseInfo>  listAllAccoutInfo();




}
