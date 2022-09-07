package com.ifx.session.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ifx.server.service.Server2ClientService;
import com.ifx.session.entity.Session;
import com.ifx.session.mapper.SessionMapper;
import com.ifx.session.service.IChatAction;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.vo.ChatMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@DubboService
@Service
@Slf4j
public class ChatAction implements IChatAction {

    @Autowired
    SessionServiceImpl sessionService;
    @Autowired
    SessionMapper sessionMapper;
    @Autowired
    SessionAccountService sessionAccountService;
    @DubboReference
    Server2ClientService server2ClientService;

    @Override
    public List<ChatMsgVo> pullOffline(String account) {
        return null;
    }

    @Override
    public void pushMsg(String fromAccount, Long sessionId, String msg) {
//        1. 存储消息
//            1.1 查询session 下所有信息
//            1.2 存储 msg 消息
//        投递成功则将消息持久化，
//        投递失败则落入存储库
        List<String> accs = sessionAccountService.listSessionAccs(sessionId);
        log.info("正在投递----消息");

        ChatMsgVo chatMsgVo = new ChatMsgVo();
//        投递消息
        accs.stream().forEach(e -> server2ClientService.sendClient(e,msg));

    }

    @Override
    public List<ChatMsgVo> pullMsg(String fromAccount, Long sessionId) {
        return null;
    }



    @Override
    public List<ChatMsgVo> pullHisMsg(Long sessionId) {
        return null;
    }

}
