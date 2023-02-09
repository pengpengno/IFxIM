package com.ifx.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.account.entity.Account;
import com.ifx.account.mapstruct.AccountHelper;
import com.ifx.account.mapper.AccountMapper;
import com.ifx.account.service.AccountService;
import com.ifx.account.utils.PasswordUtils;
import com.ifx.account.validator.ACCOUNTLOGIN;
import com.ifx.account.vo.AccountVo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.constant.CommonConstant;
import com.ifx.common.utils.ValidatorUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author wangpeng
 * @description 针对表【account(基本用户信息表)】的数据库操作Service实现
 * @createDate 2022-07-30 16:21:21
 */
@Service
@DubboService
public class AccountServiceImpl
        extends ServiceImpl<AccountMapper, Account>
        implements AccountService {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private AccountMapper accountMapper;

    @Override
    public Boolean login(AccountVo accountVo) {
        AtomicReference<Boolean> status = new AtomicReference<>(Boolean.FALSE);
        Mono.just(accountVo)
            .doFirst(()->ValidatorUtil.validateor(accountVo,ACCOUNTLOGIN.class))
            .subscribe(ac -> {
                Account account = accountMapper
                .selectOne(new LambdaQueryWrapper<Account>().
                eq(Account::getAccount, accountVo.getAccount())
                .or()
                .eq(Account::getEmail, accountVo.getEmail()));
                status.set(PasswordUtils.verityPassword(accountVo.getPassword(), account.getSalt(), account.getPwdhash()));
            });
        return status.get();
    }



    public AccountInfo loginAndGetAcc(AccountVo accountVo) {
        ValidatorUtil.validateor(accountVo,ACCOUNTLOGIN.class);
        Account account = accountMapper.selectOne(new LambdaQueryWrapper<Account>().
                eq(Account::getAccount, accountVo.getAccount())
                .or()
                .eq(Account::getAccount, accountVo.getAccount())
        );
        Boolean isLogin = PasswordUtils.verityPassword(accountVo.getPassword(), account.getSalt(), account.getPwdhash());
        if (isLogin){
            AccountInfo accountInfoVo = new AccountInfo();
            BeanUtil.copyProperties(account,accountInfoVo);
            return accountInfoVo;
        }
        return null;
    }

    @Override
    public List<AccountInfo> search(AccountSearchVo searchVo) {
        List<Account> accounts = accountMapper.selectList(new LambdaQueryWrapper<Account>()
                        .eq(Account::getEmail,searchVo.getMail())
                        .or()
                        .like(Account::getAccount, searchVo.getLikeAccount())
                        );
        return accounts.stream().map(e -> {
            AccountInfo accountInfo = new AccountInfo();
            BeanUtil.copyProperties(e, accountInfo);
            return accountInfo;
        }).collect(Collectors.toList());
    }


    @Override
    public String register(AccountVo accountVo) {
        Account account = AccountHelper.INSTANCE.transform4(accountVo);
        Account accountInfo = accountMapper.selectOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getAccount,accountVo.getAccount())
                .or()
                .eq(Account::getEmail,accountVo.getEmail()));
        if (!Objects.isNull(accountInfo)) {
            return CommonConstant.ACCOUNT_EXIT;
        }
        account.setSalt(PasswordUtils.generateSalt());
        account.setPwdhash(PasswordUtils.generatePwdHash(accountVo.getPassword(),account.getPwdhash()));
        accountMapper.insert(account);
        return account.getAccount();
    }

    @Override
    public List<Account> search(Collection<String> accounts) {
        return accountMapper.selectList(new LambdaQueryWrapper<Account>()
                .in(Account::getAccount, accounts));
    }
}




