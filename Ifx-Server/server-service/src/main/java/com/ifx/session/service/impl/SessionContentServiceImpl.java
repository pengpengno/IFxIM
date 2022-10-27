package com.ifx.session.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.session.entity.SessionContent;
import com.ifx.session.service.SessionContentService;
import com.ifx.session.mapper.SessionContentMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【session_content(会话信息表)】的数据库操作Service实现
* @createDate 2022-10-27 09:55:25
*/
@Service
public class SessionContentServiceImpl extends ServiceImpl<SessionContentMapper, SessionContent>
    implements SessionContentService{

}




