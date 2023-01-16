package com.ifx.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ifx.account.entity.Account;
import com.ifx.account.entity.AccountRelation;
import com.ifx.account.mapstruct.AccountHelper;
import com.ifx.account.mapper.AccountRelationMapper;
import com.ifx.account.service.AccountRelationService;
import com.ifx.account.service.AccountService;
import com.ifx.account.validator.ACCOUTRELATIONINSERT;
import com.ifx.account.vo.AccountRelationVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.CacheUtil;
import com.ifx.common.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
* @author HP
* @description 针对表【account_relation(账户关系表)】的数据库操作Service实现
* @createDate 2023-01-12 19:16:28
*/
@Slf4j
@Service
public class AccountRelationServiceImpl extends ServiceImpl<AccountRelationMapper, AccountRelation>
    implements AccountRelationService{


    @Resource
    private AccountService accountService;

    @Resource
    private AccountRelationMapper accountRelationMapper;

    @Resource(name = "Redis")
    private CacheUtil redisCache;


    private static final String RELATION_CACHE_KEY_PREFIX= "ACCOUNT:RELATION:";   //账户关系缓存前缀

    private static final String RELATION_SPLIT = ",";   //  账户关系分割符

    @Override
    public Set<String> listRelationWithAccount(String account) {
        Set<String> relations = CollectionUtil.newHashSet();
        listRelationCache(account)
                .switchIfEmpty(listRelationDb(account))
                .subscribe(relations::add);
        return relations;
    }

    /***
     * 从 db 中获取 账户关系图
     * @param account
     * @return
     */
    private Flux<String>  listRelationDb(String account){
        return Mono.justOrEmpty(Optional.ofNullable(account))
                .map(acc -> accountRelationMapper.selectOne(new LambdaQueryWrapper<AccountRelation>()
                        .eq(AccountRelation::getAccount, acc)))
                .flatMap(Mono::justOrEmpty)
                .map(AccountRelation::getAccount_relations)
                        .flatMap(Mono::justOrEmpty)
                                .flatMapMany(this::splitRelations)
                .onErrorResume(throwable -> Flux.empty())
                .doOnError(throwable -> log.debug(ExceptionUtil.stacktraceToOneLineString(throwable)))
                ;

    }

    /***
     * 从缓存获取关系图
     * @param account
     * @return
     */
    private Flux<String> listRelationCache(String account){
        return Mono.justOrEmpty(Optional.ofNullable(redisCache.getStr(account)))
                .flatMapMany(this::splitRelations)
                .onErrorResume(throwable -> Flux.empty())
                .doOnError(throwable -> log.debug(ExceptionUtil.stacktraceToOneLineString(throwable)))
        ;

    }

    /***
     * 分割获取 关系图
     * @param relationStr
     * @return
     */
    private Flux<String> splitRelations(String relationStr){
        if (StrUtil.isBlank(relationStr)){
            return Flux.empty();
        }
        String[] split = relationStr.split(RELATION_SPLIT);
        return Flux.just(split);
    }


    @Override
    public List<AccountInfo> listAllRelationBaseInfo(String account) {
        AtomicReference<List<Account>> accountList = new AtomicReference<>();
        Mono.justOrEmpty(Optional.ofNullable(account))
                        .map(this::listRelationWithAccount)
                                .subscribe(setRelation-> accountList.set(accountService.search(setRelation)));
        return AccountHelper.INSTANCE.trans2Info(accountList.get());
    }


    /***
     * 1. 查询缓存
     * 2. 更新缓存
     * 3. 更新DB
     * 4. 有异常则抛出
     * 5. 手动回滚数据
     * @param vo
     * @return
     */
    @Override
    public Long insertRelation(AccountRelationVo vo) {
        AtomicReference<Long> relationId = new AtomicReference<>();
        ValidatorUtil.validateor(vo, ACCOUTRELATIONINSERT.class);  // 校验参数
        Set<String> relations = listRelationWithAccount(vo.getAccount());
        Mono.just(relations)
                .doOnNext(acc -> CollectionUtil.addAll(relations,vo.getRelations()))  // 实体类添加
                .doOnNext(acc -> redisCache.set(RELATION_CACHE_KEY_PREFIX+vo.getAccount(),acc)) // 更新缓存
                .flatMap(acc-> addRelationData2DBb(vo))
                .doOnError(throwable ->  redisCache.set(RELATION_CACHE_KEY_PREFIX+vo.getAccount(),vo.getRelations()))  //错误回滚回滚缓存
                .subscribe(relationId::set);
        return relationId.get();
    }

    /***
     * 添加关系图 至数据库
     * @param accountVo
     * @return
     */
    private Mono<Long> addRelationData2DBb(AccountRelationVo accountVo){
        AccountRelation accountRelation = accountRelationMapper.selectOne(new LambdaQueryWrapper<AccountRelation>()
                .eq(AccountRelation::getAccount,accountVo));
        return Mono.justOrEmpty(Optional.ofNullable(accountRelation))
                .flatMap(relation->relation == null ? addNewRelation(accountVo)
                        :updateRelation(accountVo)
                );
    }

    /**
     * 更新数据库中的 关系
     * @param vo
     * @return
     */
    private Mono<Long> updateRelation(AccountRelationVo vo){
        AccountRelation accountRelation = vo2Entity(vo).get();
        updateById(accountRelation);
        return Mono.just(accountRelation.getId())
                .doOnError(throwable -> Boolean.TRUE , (throwable)-> log.error("更新关系失败！"));
    }

    /***
     * 添加新 关系 行
     * @param accountVo
     * @return
     */
    private Mono<Long> addNewRelation(AccountRelationVo accountVo){
        AccountRelation accountRelation = vo2Entity(accountVo).get();
        log.debug("添加了用户{} 的关系 {} ,行 id 为 {}",accountVo,accountVo.getRelations(),accountRelation.getId());
        accountRelationMapper.insert(accountRelation);
        return Mono.just(accountRelation.getId());

    }


    /***
     * 参数转实体类构造器
     * @param vo  关系Vo
     * @return
     */
    private Supplier<AccountRelation> vo2Entity(AccountRelationVo vo){
        AccountRelation accountRelation = new AccountRelation();
        accountRelation.setAccount(vo.getAccount());
        accountRelation.setAccount_relations(setRelation2Str(vo.getRelations()));
        accountRelation.setId(vo.getRelationId() == null ? IdUtil.getSnowflakeNextId(): vo.getRelationId());
        accountRelation.setActive(1);
        return () -> accountRelation;
    }

    /***
     * set 关系图 转化为关系串
     * @param relations
     * @return
     */

    private String setRelation2Str(Set<String> relations){
        return StrUtil.join(RELATION_SPLIT, relations);
    }

    /***
     * 添加 关系图缓存
     * @param accountVo
     * @param relations
     */
    private void addRelationData2Cache(String accountVo,Set<String> relations){
        redisCache.set(RELATION_CACHE_KEY_PREFIX+accountVo,setRelation2Str(relations));
    }


    /***
     * 搜索账户是否为好友 目前先这么滴吧  后面学会图关系数据处理再说 又不是不能用
     * @param account
     * @param searchAccount
     * @return  返回是否存在制定账户关系
     */
    private Mono<Boolean>  relationsHasAccount(String account ,String searchAccount){
        Set<String> relation = listRelationWithAccount(account);
        return Mono.justOrEmpty(Optional.ofNullable(relation))
                .flatMap(relatSet-> Mono.just(relatSet.contains(searchAccount)));
    }
}




