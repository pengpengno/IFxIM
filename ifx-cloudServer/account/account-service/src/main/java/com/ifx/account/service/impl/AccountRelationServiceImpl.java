package com.ifx.account.service.impl;

import com.ifx.account.service.AccountRelationService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.common.base.AccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/12
 */
@Slf4j
@Service
@DubboService
public class AccountRelationServiceImpl implements AccountRelationService {


    @Override
    public List<AccountInfo> listMyRelation(String account) {



        return null;
    }


    public Long addRelation(AccountBaseInfo accountBaseInfo){
        return null;
    }
}
