package com.ifx.session.service.impl;

import com.ifx.session.service.IChatAction;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.vo.ChatMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 消息
 */
@Service
@Slf4j
public class ChatAction implements IChatAction {


    @Autowired
    SessionAccountService sessionAccountService;



    @Override
    public List<ChatMsgVo> pullOffline(String account) {
        return null;
    }

//    @Override
//    public void pushMsg(String fromAccount, Long sessionId, String msg) {
////        1. 存储消息
//
////            1.1 查询 session 下所有信息
//
////            1.2 存储 msg 消息
//
////        用户在线则将数据落入存储库中
////        用户不在线则 落入离线库中
////        List<String> accs = sessionAccountService.listSessionAccs(sessionId);
////        log.info("正在投递----消息");
////
////        ChatMsgVo chatMsgVo = new ChatMsgVo();
////        投递消息
////        accs.stream().forEach(e -> server2ClientService.sendClient(e,msg));
//
//    }

    /***
     * 1.获取所属会话Id
     * 2. 尝试获取会话下用户状态
     * 3. 根据用户状态进行消息推送
     * 4. 写入消息推送日志
     * @param chatMsgVo  消息实体
     */
    @Override
    public void pushMsg(ChatMsgVo chatMsgVo) {
//        final  String sendMsg = chatMsgVo.get
        final Long sessionId = chatMsgVo.getSessionId();

    }

    @Override
    public List<ChatMsgVo> pullMsg(String fromAccount, Long sessionId) {
        return null;
    }



    @Override
    public List<ChatMsgVo> pullHisMsg(Long sessionId) {
        return null;
    }


    @Override
    public List<ChatMsgVo> pullHisMsgByQuery(Long sessionId) {
        return null;
    }
}
