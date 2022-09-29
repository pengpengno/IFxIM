package com.ifx.account.service;

import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.account.vo.AccountSearchVo;
import com.ifx.common.base.AccountInfo;

import java.util.List;
import java.util.Map;

/**
* @author wangpeng
* @description 针对表【account(基本用户信息表)】的数据库操作Service
* @createDate 2022-07-30 16:21:21
*/
public interface AccountService  {

        public Boolean login(AccountBaseInfo accountBaseInfo);

        public AccountInfo loginAndGetCur(AccountBaseInfo accountBaseInfo) ;

        public String register(AccountBaseInfo accountBaseInfo);

        default List<Map<String,Long>> test(Long s){
                return null;
        }

        public List<AccountInfo> search(AccountSearchVo accountSearchVo);
        // 条件查询用户信息
        public List<AccountInfo> search(Long accountSearchVo);  // 条件查询用户信息

        public List<AccountBaseInfo>  listAllAccoutInfo(); // 查询所有的账户




}
