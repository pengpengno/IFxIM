package com.ifx.account.service.impl.reactive;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ifx.account.entity.Account;
import com.ifx.account.mapstruct.AccountHelper;
import com.ifx.account.repository.impl.AccountRepositoryImpl;
import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.utils.PasswordUtils;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountException;
import java.util.function.Function;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/14
 */
@Service
@Slf4j
public class ReactiveAccountServiceImpl implements ReactiveAccountService {

    @Autowired
    private AccountRepositoryImpl accountRepository ;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    private DatabaseClient databaseClient;


    @Override
    public Mono<AccountInfo> findByAccount(String account) {
        return accountRepository.findByAccount(account)
                .map(AccountHelper.INSTANCE::buildAccountInfo)
                ;
    }




    @Override
    public Mono<AccountInfo> login(AccountVo accountVo) {
        return  accountRepository.findByAccount(accountVo.accountId())
                .flatMap(acc -> {
                    if (ObjectUtil.isNull(acc)){
                        return Mono.error(new IllegalAccessException("用户名不存在！"));
                    }else {
                        if (verifyAccount().apply(acc)){
                            return Mono.just(AccountHelper.INSTANCE.buildAccountInfo(acc));
                        }else {
                            return Mono.error( new AccountException("密码错误！"));
                        }
                    }
                });
    }

    public Mono<AccountInfo> register(AccountVo accountVo) {
        Assert.notNull(accountVo,"注册账户不可为空！");
        return Mono.defer(()-> accountRepository.findByAccount(accountVo.getAccount())
                .hasElement()
                .flatMap((hasEle)-> {
                    if (hasEle){
                        return Mono.error(new AccountException("用户已注册！"));
                    }else {
                        return r2dbcEntityTemplate.insert( registerAccount().apply(accountVo))
                                .map(AccountHelper.INSTANCE::buildAccountInfo);
                    }
                }));
    }

    private Function<Account,Boolean>  verifyAccount(){
        return (account ) -> PasswordUtils.verityPassword(account.getPassword(), account.getSalt(), account.getPwdhash());
    }



    private Function<AccountVo,Account> registerAccount(){
        return (accountVo)-> {
            Account account = AccountHelper.INSTANCE.bulidAccount(accountVo);
            account.setId(IdUtil.getSnowflakeNextId());
            String salt = PasswordUtils.generateSalt();
            account.setSalt(salt);
            account.setPwdhash(PasswordUtils.generatePwdHash(accountVo.getPassword(),salt));
            return account;
        };
    }


}