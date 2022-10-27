package com.ifx.session.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ifx.session.entity.Session;
import com.ifx.session.vo.SessionCreateVo;

/**
* @author HP
* @description 针对表【session(会话表)】的数据库操作Service
* @createDate 2022-09-28 16:28:00
*/
public interface SessionService extends IService<Session> {

    /**
     * 创建会话
     * @return
     */
    Long newSession();

    /**
     * 
     * @param sessionId
     * @return
     */
    Session getSession(Long sessionId);
}
