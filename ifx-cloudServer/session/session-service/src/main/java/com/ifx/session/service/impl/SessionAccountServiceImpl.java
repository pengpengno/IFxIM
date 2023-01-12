package com.ifx.session.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.common.utils.CacheUtil;
import com.ifx.session.consts.IFxCommonConstants;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.mapper.SessionAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
* @author HP
* @description 针对表【session_account(会话账户中间表)】的数据库操作Service实现
* @createDate 2022-09-28 16:35:38
*/
@Service
@Slf4j
@DubboService
public class SessionAccountServiceImpl extends ServiceImpl<SessionAccountMapper, SessionAccount>
    implements SessionAccountService{
    @Resource(name = "Redis")
    private CacheUtil cacheUtil;

//    private static String sessionCache =
    @Override
    public List<String> listAccBySessionId(Long sessionId) {
        if (sessionId == null){
            return CollectionUtil.newArrayList();
        }
        List<String> list = cacheUtil.get(sessionId.toString(), List.class);
        List<String> sessionIds = list == null?
                ((Supplier<List<String>>) () -> {
                    SessionAccount sessionAccount = getById(sessionId);
                    if (sessionAccount == null || StrUtil.isBlank(sessionAccount.getAccountIds())) {
                        return CollectionUtil.newArrayList();
                    }
                    String[] split = sessionAccount.getAccountIds().split(IFxCommonConstants.ACCOUNT_SPLIT);
                    return Arrays.stream(split)
                            .filter(StrUtil::isNotBlank)
                    .collect(Collectors.toList());
                }).get() : list;
        cacheUtil.set(sessionId.toString(),sessionIds);
        return sessionIds;
    }
}




