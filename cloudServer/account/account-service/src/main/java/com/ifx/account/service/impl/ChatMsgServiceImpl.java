package com.ifx.account.service.impl;

import com.ifx.account.service.ChatMsgService;
import com.ifx.account.vo.ChatMsgVo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
* @author HP
* @description 针对表【chat_msg(信息表)】的数据库操作Service实现
* @createDate 2023-01-16 16:52:17
*/
@Service
public class ChatMsgServiceImpl  implements ChatMsgService {


    @Override
    public Mono<Void> pushMsg(ChatMsgVo chatMsgVo) {
        return Mono.empty();

    }


    @Override
    public Mono<Boolean> chat(ChatMsgVo chatMsgVo) {
        return null;
    }
}




