package com.ifx.session.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.session.entity.ChatMsg;
import com.ifx.session.service.ChatMsgService;
import com.ifx.session.mapper.ChatMsgMapper;
import com.ifx.session.vo.ChatMsgVo;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【chat_msg(信息表)】的数据库操作Service实现
* @createDate 2023-01-16 16:52:17
*/
@Service
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper, ChatMsg>
    implements ChatMsgService{


    @Override
    public void pushMsg(ChatMsgVo chatMsgVo) {


    }
}




