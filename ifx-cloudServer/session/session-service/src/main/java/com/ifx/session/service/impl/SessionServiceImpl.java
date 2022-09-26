package com.ifx.session.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.CacheUtil;
import com.ifx.session.entity.Session;
import com.ifx.session.service.SessionService;
import com.ifx.session.mapper.SessionMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
* @author HP
* @description 针对表【session(会话表)】的数据库操作Service实现
* @createDate 2022-08-23 15:16:25
*/
@Service
@DubboService
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session>
    implements SessionService {

    @Resource
    private SessionMapper sessionMapper;


    @Resource(name = "Redis")
    private CacheUtil redisCache;

    private static String defaultSessionName = "会话";

    @Override
    public Long newSession() {
//        1. 创建新会话 加入缓存, 设定时延销毁
//        2. 返回seesion id 保持Session 处于挂起状态,
        Long id = IdUtil.getSnowflakeNextId();
        Session session = spSession().get();
        session.setId(id);
        session.setSessionName(defaultSessionName);
        redisCache.expire(id.toString(),session,50L, TimeUnit.MILLISECONDS);
//        sessionMapper.insert(session);
        return id;
    }


    @Override
    public Boolean addAcc(Long sessionId,AccountInfo accountInfo) {

        return null;
    }

    private Supplier<Session> spSession(){
        return ()-> {
            Session session = new Session();
            session.setActive(1);
            session.setCreateTime(DateUtil.date());
            session.setUpdateTime(DateUtil.date());
            session.setVersion(1);
            return session;
        };
    }



}




