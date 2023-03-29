package com.ifx.session.service.impl;

import com.ifx.session.service.ChatMsgService;
import com.ifx.session.vo.ChatMsgVo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
* @author HP
* @description 针对表【chat_msg(信息表)】的数据库操作Service实现
* @createDate 2023-01-16 16:52:17
*/
@Service
public class ChatMsgServiceImpl  implements ChatMsgService{


    @Override
    public void pushMsg(ChatMsgVo chatMsgVo) {


    }


    @Override
    public Mono<Boolean> chat(ChatMsgVo chatMsgVo) {
        return null;
    }
}




