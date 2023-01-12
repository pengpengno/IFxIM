package com.ifx.account.service;

import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.common.base.AccountInfo;

import java.util.List;

/**
* @author wangpeng
* @description 针对表【account(基本用户信息表)】的数据库操作Service
* @createDate 2022-07-30 16:21:21
*/
public interface AccountService  {
        /***
         * 登陆接口
         * @param accountBaseInfo
         * @return 返回登陆是否成功
         */

        public Boolean login(AccountBaseInfo accountBaseInfo);

        /***
         * 登陆接口
         * @param accountBaseInfo
         * @return 返回登陆成功后的用户实体
         */
        public AccountInfo loginAndGetCur(AccountBaseInfo accountBaseInfo) ;


        /***
         * 注册账户信息
         * @param accountBaseInfo
         * @return
         */
        public String register(AccountBaseInfo accountBaseInfo);


        public List<AccountInfo> search(AccountSearchVo accountSearchVo);

        public List<AccountBaseInfo>  listAllAccoutInfo(); // 查询所有的账户




}
