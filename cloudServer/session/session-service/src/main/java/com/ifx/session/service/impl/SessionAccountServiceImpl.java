package com.ifx.session.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.mapstruct.SessionAccMap;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.vo.session.SessionAccountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author HP
* @description 针对表【session_account(会话账户中间表)】的数据库操作Service实现
* @createDate 2022-09-28 16:35:38
*/
@Service
@Slf4j
public class SessionAccountServiceImpl implements SessionAccountService{

//    @Resource
//    private ReactiveAccountService accountService;


    private static final String SESSION_ACC_CACHE_KEY_PREFIX = "SESSION:ACCOUNT:";   //账户关系缓存前缀

    private static final String SESSION_ACC_LOCK_PREFIX = "SESSION:ACCOUNT:LOCK:" ;  // 锁前缀

    private static final String SPLIT = ",";   //  账户关系分割符


    @Override
    public Flux<String> listAccBySessionId(Long sessionId) {
         Set<String> res = new HashSet<>();
         listAccBySessionInCache2Set(sessionId)
                .switchIfEmpty(listAccSetBySessionInDb(sessionId));
                /*.subscribe(re-> CollectionUtil.addAll(res,re));*/
//         return res;
         return null;
    }

    @Override
    public Mono<SessionAccount> addAcc2Session(SessionAccountVo createVo) {
//        ValidatorUtil.validateOne(createVo, SessionAccountAdd.class);
//        return addAcc2Session0(createVo);
        return null;
    }

    private SessionAccount addAcc2Session0(SessionAccountVo createVo){
        Long sessionId = createVo.getSessionId();
        getSessionAcc(sessionId,createVo.getCreateInfo().getUserId());
        SessionAccount transform = SessionAccMap.INSTANCE.transform(createVo);
//        mapper.insert(transform);
        return transform;
    }



    private Mono<Long> addSessionAcc2Db(SessionAccount sessionAccount){
//        return Mono.justOrEmpty(
//                Optional.ofNullable(
//                        getSessionAccId(sessionAccount.getSessionId(),sessionAccount.getUserId())))
//            .switchIfEmpty(Mono.just(sessionAccount)
//                            .flatMap((k)->{
//                                sessionAccount.setId(IdUtil.getSnowflakeNextId());
//                                mapper.insert(sessionAccount);
//                                return Mono.just(sessionAccount.getId());
//                            }));
        return null;
    }


    private SessionAccount getSessionAcc(Long sessionId, Long userId){
//         return mapper.selectOne(new LambdaQueryWrapper<SessionAccount>()
//                .eq(SessionAccount::getSessionId,sessionId)
//                .eq(SessionAccount::getUserId,userId));
        return null;
    }

    private Long getSessionAccId(Long sessionId, Long userId){
        SessionAccount sessionAcc = getSessionAcc(sessionId, userId);
        return sessionAcc == null? null:sessionAcc.getId();
    }



    /***
     * db 中查询会话下账户
     * @param sessionId
     * @return 为空则返回 Mono.empty()
     */
    private Mono<List<SessionAccount>> listAccBySessionInDb(Long sessionId){
//        return Mono.justOrEmpty(Optional.ofNullable(sessionId))
//            .map(id -> mapper.selectList(new LambdaQueryWrapper<SessionAccount>()
//                    .eq(SessionAccount::getSessionId, sessionId)))
//                .flatMap(acc -> CollectionUtil.isNotEmpty(acc)? Mono.just(acc) : Mono.empty());
        return null;
    }

    /***
     * db 中查询会话下账户
     * @param sessionId
     * @return
     */
    private Mono<Set<String>> listAccSetBySessionInDb(Long sessionId){
        return listAccBySessionInDb(sessionId)
                .flatMap(acc-> Mono
                        .just(acc.stream()
                        .filter(Objects::nonNull)
                        .map(k->k.getUserId().toString())
                        .collect(Collectors.toSet())));
    }

    /***
     * 缓存中查询会话下账户
     * @param sessionId 会话Id
     * @return
     */
    private Mono<String> listAccBySessionInCache(Long sessionId){

        return null;
//        return Mono.justOrEmpty(Optional.ofNullable(sessionId))
//                .flatMap(id ->
//                    Mono.justOrEmpty(Optional.ofNullable( cache.getStr(SESSION_ACC_CACHE_KEY_PREFIX + id))));
    }
    /***
     * 缓存中查询会话下账户
     * @param sessionId 会话Id
     * @return
     */
    private Mono<Set<String>> listAccBySessionInCache2Set(Long sessionId){
        return listAccBySessionInCache(sessionId)
                .flatMap(id -> Mono.just(str2Set(id)));
    }


    private String set2String(Set<String> set){
        return String.join(SPLIT, set);
    }


    private Set<String> str2Set(String str){
        return new HashSet<>(StrUtil.split(str, SPLIT));
    }

    private Mono<Boolean> addAccWithSessionId2Cache(Long sessionId,Set<String> accs){
        if (accs == null){
            return Mono.empty();
        }
        return null;
//        return Mono.justOrEmpty(Optional.ofNullable(sessionId))
//            .map(id-> cache.set(SESSION_ACC_CACHE_KEY_PREFIX+sessionId,set2String(accs)));
    }









}




