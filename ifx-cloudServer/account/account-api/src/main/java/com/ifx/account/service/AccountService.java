package com.ifx.account.service;

import com.ifx.account.entity.Account;
import com.ifx.account.vo.AccountVo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.common.base.AccountInfo;

import java.util.Collection;
import java.util.List;

/**
* @author wangpeng
* @description 针对表【account(基本用户信息表)】的数据库操作Service
* @createDate 2022-07-30 16:21:21
*/
public interface AccountService  {
        /***
         * 登陆接口
         * @param accountVo
         * @return 返回登陆是否成功
         */

        public Boolean login(AccountVo accountVo);

        /***
         * 登陆接口
         * @param accountVo
         * @return 返回登陆成功后的用户实体
         */
        public AccountInfo loginAndGetAcc(AccountVo accountVo) ;


        /***
         * 注册账户信息
         * @param accountVo
         * @return 返回注册后的账户 account
         */
        public String register(AccountVo accountVo);

        /***
         * 搜索用户
         * @param accountSearchVo
         * @return
         */
        public List<AccountInfo> search(AccountSearchVo accountSearchVo);

        /***
         * 查询用户信息
         * @param accounts
         * @return
         */
        public List<Account> search(Collection<String> accounts);



}
