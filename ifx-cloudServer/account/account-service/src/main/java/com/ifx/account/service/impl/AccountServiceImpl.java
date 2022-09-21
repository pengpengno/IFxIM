package com.ifx.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.account.helper.AccountHelper;
import com.ifx.account.entity.Account;
import com.ifx.account.mapper.AccountMapper;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.account.vo.AccountSearchVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.constant.CommonConstant;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wangpeng
 * @description 针对表【account(基本用户信息表)】的数据库操作Service实现
 * @createDate 2022-07-30 16:21:21
 */
@Service
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
        Account account = accountMapper.selectOne( new QueryWrapper<Account>().
                eq("account", accountBaseInfo.getAccount())
        .or()
        .eq("email", accountBaseInfo.getAccount()));
        if (Objects.isNull(account)) {
            return isLogin;
        }
        if (accountBaseInfo.getPassword().equals(account.getPassword())) {
            return !isLogin;
        }
        return isLogin;
    }

    public AccountInfo loginAndGetCur(AccountBaseInfo accountBaseInfo) {
        if (Objects.isNull(accountBaseInfo)) {
            return null;
        }
        if (Objects.isNull(accountBaseInfo.getPassword())) {
            return null;
        }
        Account account = accountMapper.selectOne(new QueryWrapper<Account>().
                eq("account", accountBaseInfo.getAccount())
                .or()
                .eq("email", accountBaseInfo.getAccount())
        );
        if (Objects.isNull(account)) {
            return null;
        }
        if (StrUtil.equals(account.getPassword(),accountBaseInfo.getPassword())){
            AccountInfo accountInfoVo = new AccountInfo();
            BeanUtil.copyProperties(account,accountInfoVo);
            return accountInfoVo;
        }
        return null;
    }

    @Override
    public List<AccountInfo> search(AccountSearchVo searchVo) {
        List<Account> accounts = accountMapper.selectList(new QueryWrapper<Account>()
//                .eq(searchVo.getMail()!=null && StrUtil.isNotBlank(searchVo.getAccount()),"account",searchVo.getAccount())
                        .eq(searchVo.getMail() != null && StrUtil.isNotBlank(searchVo.getMail()),
                                "email", searchVo.getMail())
                        .or()
                        .like(searchVo.getLikeAccount() != null && StrUtil.isNotBlank(searchVo.getLikeAccount()),
                                "account", searchVo.getLikeAccount())

        );
        List<AccountInfo> accountInfos = accounts.stream().map(e -> {
            AccountInfo accountInfo = new AccountInfo();
            BeanUtil.copyProperties(e, accountInfo);
            return accountInfo;
        }).collect(Collectors.toList());
        return accountInfos;
    }

    @Override
    public List<AccountInfo> search(Long accountSearchVo) {
        return null;
    }

    @Override
    public String register(AccountBaseInfo accountBaseInfo) {
        Account instance = Account.getInstance();
        Account account = AccountHelper.INSTANCE.transform4(accountBaseInfo, instance);
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", accountBaseInfo.getAccount());
        queryWrapper.or();
        queryWrapper.eq("email", accountBaseInfo.getAccount());
        Account accountInfo = accountMapper.selectOne(queryWrapper);
        if (!Objects.isNull(accountInfo)) {
            return CommonConstant.ACCOUNT_EXIT;
        }
        accountMapper.insert(account);
        return account.getAccount();
    }

    @Override
    public List<AccountBaseInfo> listAllAccoutInfo() {
        Page<Account> page = page(new Page<>(), new QueryWrapper<Account>().eq("account", "wangpeng"));
        List<AccountBaseInfo> collect = page.getRecords().stream().map(e -> AccountHelper.INSTANCE.transform4Init(e)).collect(Collectors.toList());
        return collect;
    }
}




