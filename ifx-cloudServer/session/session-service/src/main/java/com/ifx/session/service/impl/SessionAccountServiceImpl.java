package com.ifx.session.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.mapper.SessionAccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author HP
* @description 针对表【session_account(会话账户中间表)】的数据库操作Service实现
* @createDate 2022-09-28 16:35:38
*/
@Service
public class SessionAccountServiceImpl extends ServiceImpl<SessionAccountMapper, SessionAccount>
    implements SessionAccountService{


    @Override
    public List<String> listAccBySessionId() {
        return null;
    }
}




