package com.ifx.account.service.reactive;

import com.ifx.account.vo.AccountRelationVo;
import com.ifx.common.base.AccountInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/17
 */
public interface ReactiveAccountRelationService {


    public Flux<String> listRelationWithAccount(String account);

    /***
     * 获取所有好友关系基本信息
     * @param account
     * @return
     */

    public Flux<AccountInfo> listAllRelationBaseInfo(String account);


    /***
     * 添加账户关系
     * @param vo
     * @return
     */
    public Mono<Long> insertRelation(AccountRelationVo vo);
}
