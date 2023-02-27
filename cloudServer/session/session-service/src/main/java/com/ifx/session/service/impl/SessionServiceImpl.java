package com.ifx.session.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ifx.common.utils.CacheUtil;
import com.ifx.session.mapstruct.SessionMapper;
import com.ifx.session.service.SessionService;
import com.ifx.session.vo.session.SessionInfoVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.function.Supplier;

/**
* @author HP
* @description 针对表【session(会话表)】的数据库操作Service实现
* @createDate 2022-09-28 16:23:47
*/
@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    @Resource(name = "Redis")
    private CacheUtil cacheUtil;

    @Resource
    private SessionAccountServiceImpl sessionAccountService;

    @Resource
    private SessionMapper mapper;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Resource
    private ReactiveRedisTemplate<String,Object> redisTemplate;

    @Override
    public Mono<Long> post2Session(SessionInfoVo sessionInfoVo) {
        return Mono.just(sessionInfoVo)
            .map(SessionMapper.INSTANCE::transform)
                .flatMap(session ->{
                    Supplier<Long> id = session.getSessionId()==null? () -> {
                        session.setId(IdUtil.getSnowflakeNextId());
                        r2dbcEntityTemplate.insert(session);
                        return session.getSessionId();
                    } : ()-> {
                        r2dbcEntityTemplate.update(session);
                        return session.getId();
                    };
                    return Mono.just(id.get());
                });
    }

}




