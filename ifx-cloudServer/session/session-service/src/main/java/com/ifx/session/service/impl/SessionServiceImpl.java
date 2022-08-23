package com.ifx.session.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.session.entity.Session;
import com.ifx.session.service.SessionService;
import com.ifx.session.mapper.SessionMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【session(会话表)】的数据库操作Service实现
* @createDate 2022-08-23 15:16:25
*/
@Service
//@DubboService
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session>
    implements SessionService {




}




