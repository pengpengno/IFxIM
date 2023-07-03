package com.ifx.account.service.impl.reactive;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ifx.account.entity.AccountRelation;
import com.ifx.account.service.reactive.ReactiveAccountRelationService;
import com.ifx.account.vo.AccountRelationVo;
import com.ifx.common.base.AccountInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * @author pengpeng
 * @description
 * @date 2023/2/17
 */
@Slf4j
@Service
public class ReactiveAccountRelationServiceImpl implements ReactiveAccountRelationService, BeanPostProcessor
{
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

//    @Autowired
    private ReactiveRedisTemplate<String,Object> redisTemplate;


    /***
     * 关系缓存key
     */
    private final String RELATION_CACHE_KEY = "ACC:RELATION:";

    private static final String RELATION_CACHE_KEY_PREFIX= "ACCOUNT:RELATION:";   //账户关系缓存前缀

    private static final String RELATION_GRAPH_CACHE_KEY_PREFIX= "RELATION:GRAPH";   //账户关系缓存前缀

    private static final String RELATION_SPLIT = ",";   //  账户关系分割符

    private Cache<String, Graph<String,DefaultEdge>> caffeine ;

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        caffeine = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(Duration.ofMinutes(30))
//                .removalListener()
                .build();
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }


    @Override
    public Mono<AccountRelationVo> listRelationWithAccount(String account) {
        Assert.notNull(account,"账户信息不允许为空！");
        return Mono.justOrEmpty(Optional.of(account))
                .flatMapMany(acc -> r2dbcEntityTemplate.select(Query.query(Criteria.where("account").is(account)),AccountRelation.class))
//                .cache()
                .map(AccountRelation::getAccountRelations)
                .flatMap(this::splitRelations)
                .collect(Collectors.toSet())
                .flatMap(li -> Mono.just(AccountRelationVo.builder()
                        .account(account)
                        .friendAccountIds(li)
                        .build()))
                .onErrorResume(throwable -> Mono.empty())
                ;
    }

    @Override
    public Flux<AccountInfo> listAllRelationBaseInfo(String account) {
        return null;
    }

    @Override
    public Mono<Long> insertRelation(@NonNull AccountRelationVo vo) {

//        Caffeine.newBuilder().build().put();
        return null;
    }


    private Mono<Void> generateRelationGraph(Flux<String> flux,String account){
        Assert.notNull(account,"传入账户信息不可为空！");
        try{
            Graph<String,DefaultEdge> graph = caffeine.get(RELATION_GRAPH_CACHE_KEY_PREFIX, () -> new DirectedMultigraph<String,DefaultEdge>(DefaultEdge.class));
           return flux.doOnEach(acc ->{
                String targetVertex = acc.get();
                graph.addVertex(targetVertex);
                graph.addEdge(account, targetVertex);
            }).doOnNext((l)->caffeine.put(RELATION_GRAPH_CACHE_KEY_PREFIX,graph))
                    .then(Mono.empty());
        }
        catch (Exception exception){
            return Mono.empty();
        }
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


    /***
     * 添加新 关系 行
     * @param accountVo
     * @return
     */
    private Mono<Long> addNewRelation(AccountRelationVo accountVo){
        AccountRelation accountRelation = vo2Entity(accountVo).get();
        log.debug("添加了用户{} 的关系 {} ,行 id 为 {}",accountVo,accountVo.getFriendAccountIds(),accountRelation.getId());
//        accountRelationMapper.insert(accountRelation);
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
        accountRelation.setAccountRelations(setRelation2Str(vo.getFriendAccountIds()));
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
//        redisCache.set(RELATION_CACHE_KEY_PREFIX+accountVo,setRelation2Str(relations));
    }


    /***
     * 搜索账户是否为好友
     * @param account
     * @param searchAccount
     * @return  返回是否存在制定账户关系
     */
    private Mono<Boolean> relationsHasAccount(String account ,String searchAccount){
        return listRelationWithAccount(account)
                .hasElement();
    }
}
