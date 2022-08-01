package com.ifx.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.account.entity.Account;
import com.ifx.account.service.AccountService;
import com.ifx.account.mapper.AccountMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author wangpeng
* @description 针对表【account(基本用户信息表)】的数据库操作Service实现
* @createDate 2022-07-30 16:21:21
*/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{
    @Resource
    private MongoTemplate mongoTemplate;
    @Override
    public Boolean login() {
        return null;
    }
}




