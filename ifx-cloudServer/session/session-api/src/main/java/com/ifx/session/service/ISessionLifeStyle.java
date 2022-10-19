package com.ifx.session.service;

import com.ifx.session.vo.SessionCreateVo;

import java.util.Set;

public interface ISessionLifeStyle {

    /**
     * 初始化会话 缓存中临时创建会话
     * @return 返回会话Id
     */
    public Long initialize();

    /**
     * 初始化会话 添加参与人
     * @param accounts 参与人
     * @return
     */
    public Long initialize(Set<String> accounts);

    /**
     * 创建会话任务
     * @param sessionCreateVo
     * @return
     */
    public Long create(SessionCreateVo sessionCreateVo);

    public void start();

    public void hangOn();

    public Boolean reConnect();

    public Boolean release();




}
