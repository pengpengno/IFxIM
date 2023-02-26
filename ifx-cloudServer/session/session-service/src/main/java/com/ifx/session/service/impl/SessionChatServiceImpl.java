package com.ifx.session.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.session.entity.SessionChat;
import com.ifx.session.mapper.SessionChatMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【session_chat(会话信息表)】的数据库操作Service实现
* @createDate 2023-01-16 16:52:51
*/
@Service
public class SessionChatServiceImpl extends ServiceImpl<SessionChatMapper, SessionChat>
    implements IService<SessionChat> {

}




