package com.ifx.account.service;

import com.ifx.common.base.AccountInfo;

import java.util.List;

/**
 * 账户关系服务
 * @author pengpeng
 * @description
 * @date 2023/1/12
 */
public interface AccountRelationService {
    /***
     * 获取用户关系网
     * @param account
     * @return
     */
    public List<AccountInfo>  listMyRelation(String account);
}
