package com.ifx.session.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.common.utils.CacheUtil;
import com.ifx.session.entity.Session;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.mapper.SessionMapper;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.service.SessionService;
import com.ifx.session.vo.SessionCreateVo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
* @author HP
* @description 针对表【session(会话表)】的数据库操作Service实现
* @createDate 2022-09-28 16:23:47
*/
@Service
@DubboService
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session>
        implements SessionService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "Redis")
    private CacheUtil cacheUtil;

    @Resource
    private SessionAccountServiceImpl sessionAccountService;
    @Override
    public Long newSession() {
        Session session = new Session();
        Long sessionId = IdUtil.getSnowflakeNextId();
        session.setSessionId(sessionId);
        session.setUpdateTime(LocalDateTime.now());
        session.setUpdateTime(LocalDateTime.now());
        cacheUtil.expire(sessionId.toString(),session,50L, TimeUnit.MINUTES);
        return sessionId;
    }


}




