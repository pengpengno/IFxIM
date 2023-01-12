package com.ifx.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.account.entity.AccountRelation;
import com.ifx.account.service.AccountRelationService;
import com.ifx.account.mapper.AccountRelationMapper;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.account.vo.AccountRelationVo;
import com.ifx.account.vo.search.AccountRelationSearch;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
* @author HP
* @description 针对表【account_relation(账户关系表)】的数据库操作Service实现
* @createDate 2023-01-12 19:16:28
*/
@Service
public class AccountRelationServiceImpl extends ServiceImpl<AccountRelationMapper, AccountRelation>
    implements AccountRelationService{


    @Resource
    private AccountService accountService;

    @Resource
    private AccountRelationMapper accountRelationMapper;

    @Override
    public Set<String> listRelationWithAccount(String account) {

        accountRelationMapper.selectOne(new LambdaQueryWrapper<AccountRelation>()
                .eq(AccountRelation::getAccount,account))


        return null;
    }


    @Override
    public List<AccountBaseInfo> listAllRelationBaseInfo(String account) {
        return null;
    }

    @Override
    public Long insertRelation(AccountRelationVo vo) {

        return null;
    }

    /***
     * 搜索账户是否为好友
     * @param account
     * @param searchAccount
     * @return
     */
    private Boolean  relationsHasAccount(String account ,String searchAccount){
//        Mono.just()
                return null;

    }
}




