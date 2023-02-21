package com.ifx.session.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.common.utils.CacheUtil;
import com.ifx.session.entity.Session;
import com.ifx.session.mapper.SessionMapper;
import com.ifx.session.service.SessionService;
import com.ifx.session.vo.session.SessionInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author HP
* @description 针对表【session(会话表)】的数据库操作Service实现
* @createDate 2022-09-28 16:23:47
*/
@Service
@DubboService
@Slf4j
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session>
        implements SessionService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "Redis")
    private CacheUtil cacheUtil;

    @Resource
    private SessionAccountServiceImpl sessionAccountService;

    @Resource
    private SessionMapper mapper;

    @Override
    public Long addorUpSession(SessionInfoVo sessionInfoVo) {
        Session transform = com.ifx.session.mapstruct.SessionMapper.INSTANCE.transform(sessionInfoVo);
        if (transform.getSessionId()!=null){
            transform.setId(IdUtil.getSnowflakeNextId());
            mapper.insert(transform);
            return transform.getId();
        }else {
            mapper.updateById(transform);
        }
        return transform.getId();
    }

}




