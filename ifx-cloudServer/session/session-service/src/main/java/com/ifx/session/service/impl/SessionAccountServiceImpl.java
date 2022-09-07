package com.ifx.session.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.account.entity.Account;
import com.ifx.session.entity.Session;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.mapper.SessionAccountMapper;
import com.ifx.session.mapper.SessionMapper;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.service.SessionService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@DubboService
public class SessionAccountServiceImpl extends ServiceImpl<SessionAccountMapper, SessionAccount>
        implements SessionAccountService {

    @Resource
    private SessionService sessionService;
    @Resource
    private SessionAccountMapper sessionAccountMapper;

    public List<String> listSessionAccs(Long sessionId) {

        List<SessionAccount> sessionAccounts = sessionAccountMapper.selectList(new QueryWrapper<SessionAccount>()
                .eq("session_id", sessionId).select("account"));
        List<String> collect = sessionAccounts.stream().map(e -> e.getAccount()).distinct().collect(Collectors.toList());
        return collect;
    }
    @Override
    public List<Account> listSessionAcc(Long sessionId) {

        List<SessionAccount> sessionAccounts = sessionAccountMapper.selectList(new QueryWrapper<SessionAccount>()
                .eq("session_id", sessionId).select("account_id"));
        List<String> collect = sessionAccounts.stream().map(e -> e.getAccount()).distinct().collect(Collectors.toList());
        return null;
    }
}
