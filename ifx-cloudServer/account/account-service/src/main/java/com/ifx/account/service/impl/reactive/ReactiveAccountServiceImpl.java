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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountException;
import java.util.function.Function;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/14
 */
@Service
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
                .switchIfEmpty(Mono.defer(()-> Mono.just( AccountInfo.builder().account(account).build())));
    }




    @Override
    public Mono<AccountInfo> login(AccountVo accountVo) {
        return  accountRepository.findByAccount(accountVo.accountId())
                .handle((acc,sink)-> {
                    if (ObjectUtil.isNull(acc)){
                        sink.error(new AccountException("用户名不存在！"));
                    }else {
                        if (verifyAccount().apply(acc)){
                            sink.next(AccountHelper.INSTANCE.buildAccountInfo(acc));
                        }else {
                            sink.error( new IllegalAccessException("密码错误！"));
                        }
                    }
                    sink.complete();
                });

    }

    public Mono<AccountInfo> register(AccountVo accountVo) {
        return  findByAccount(accountVo.getAccount())
                .handle((acc,sink)-> {
                    if (ObjectUtil.isNotNull(acc)){
                        sink.error(new AccountException("用户名已存在"));
                    }else {
                        Account account = registerAccount().apply(accountVo);
                        r2dbcEntityTemplate.insert(account).doOnNext(ac -> sink.next(AccountHelper.INSTANCE.buildAccountInfo(ac)));
                    }
                    sink.complete();
                });
    }

    private Function<Account,Boolean>  verifyAccount(){
        return (account ) -> PasswordUtils.verityPassword(account.getPassword(), account.getSalt(), account.getPwdhash());
    }

    private Function<AccountVo,Account> registerAccount(){
        return (accountVo)-> {
            Account account = AccountHelper.INSTANCE.bulidAccount(accountVo);
            account.setId(IdUtil.getSnowflakeNextId());
            account.setSalt(PasswordUtils.generateSalt());
            account.setPwdhash(PasswordUtils.generatePwdHash(accountVo.getPassword(),account.getPwdhash()));
            return account;
        };
    }


}
