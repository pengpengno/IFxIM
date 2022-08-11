package com.ifx.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.account.Helper.AccountHelper;
import com.ifx.account.entity.Account;
import com.ifx.account.mapper.AccountMapper;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.common.constant.CommonConstant;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wangpeng
 * @description 针对表【account(基本用户信息表)】的数据库操作Service实现
 * @createDate 2022-07-30 16:21:21
 */
@Service
//@DubboService(version = "1.0.0")
@DubboService
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private AccountMapper accountMapper;

    @Override
    public Boolean login(AccountBaseInfo accountBaseInfo) {
        Boolean isLogin = false;
        if (Objects.isNull(accountBaseInfo)) {
            return isLogin;
        }
        if (Objects.isNull(accountBaseInfo.getPassword())) {
            return isLogin;
        }
        QueryWrapper queryWrapper = new QueryWrapper();


        queryWrapper.eq("account", accountBaseInfo.getAccount());
        queryWrapper.or();
        queryWrapper.eq("email", accountBaseInfo.getAccount());
        Account account = accountMapper.selectOne(queryWrapper);
        if (Objects.isNull(account)) {
            return isLogin;
        }
        if (accountBaseInfo.getPassword().equals(account.getPassword())) {
            return !isLogin;
        }
        return isLogin;
    }

    @Override
    public String register(AccountBaseInfo accountBaseInfo) {
        Account instance = Account.getInstance();
        Account account = AccountHelper.INSTANCE.transform4(accountBaseInfo, instance);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", accountBaseInfo.getAccount());
        queryWrapper.or();
        queryWrapper.eq("email", accountBaseInfo.getAccount());
        Account accountInfo = accountMapper.selectOne(queryWrapper);
        if (!Objects.isNull(accountInfo)) {
            return CommonConstant.ACCOUNT_EXIT;
        }
        int row = accountMapper.insert(account);
        if (row == CommonConstant.SUCCESS) {
            return account.getAccount();
        }
        return null;
    }
}




