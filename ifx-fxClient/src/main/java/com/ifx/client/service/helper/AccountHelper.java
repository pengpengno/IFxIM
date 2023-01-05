package com.ifx.client.service.helper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson2.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.account.vo.AccountSearchVo;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@Slf4j
public class AccountHelper {

    @SneakyThrows
    public Protocol applyLogins(AccountBaseInfo vo){
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(AccountService.class, "loginAndGetCur", CollectionUtil.newArrayList(vo));
        Protocol protocol = new DubboProtocol();
        protocol.setProtocolBody(JSON.toJSONString(metaData));
        protocol.setType(IFxMsgProtocol.LOGIN_MSG_HEADER);
        return protocol;
    }

    /**
     * 废弃 请使用下方接口实现
     * {@link ProtocolHelper}
     * @see ProtocolHelper
     * @param vo
     * @return
     */
    @SneakyThrows
    @Deprecated
    public Protocol applySearch(AccountSearchVo vo){
        Method search = AccountService.class.getMethod("search", AccountSearchVo.class);
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(AccountService.class, "search", CollectionUtil.newArrayList(vo));
        Protocol protocol = new DubboProtocol();
        protocol.setProtocolBody(JSON.toJSONString(metaData));
        return protocol;
    }



    public void sendLogin(){


    }

    public static void main(String[] args) {
        StrBuilder mRxOperatorsText = new StrBuilder();
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            mRxOperatorsText.append("Observable emit 1" + "\n");
            log.info("Observable emit 1" + "\n");
            e.onNext(1);
            mRxOperatorsText.append("Observable emit 2" + "\n");
            log.info( "Observable emit 2" + "\n");
            e.onNext(2);
            mRxOperatorsText.append("Observable emit 3" + "\n");
            log.info( "Observable emit 3" + "\n");
            e.onNext(3);
            e.onComplete();
            mRxOperatorsText.append("Observable emit 4" + "\n");
            e.onNext(4);
            log.info( "Observable emit 4" + "\n");

        }).subscribe(new Observer<Integer>() {
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxOperatorsText.append("onSubscribe : " + d.isDisposed() + "\n");
                log.info( "onSubscribe : " + d.isDisposed() + "\n" );
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                mRxOperatorsText.append("onNext : value : " + integer + "\n");
                log.info( "onNext : value : " + integer + " i = {}\n",i );
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mRxOperatorsText.append("onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                    mDisposable.dispose();
                    log.info("stop onNext");
//                    log.info( "onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mRxOperatorsText.append("onError : value : " + e.getMessage() + "\n");
                log.info( "onError : value : " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                mRxOperatorsText.append("onComplete" + "\n");
                log.info( "onComplete" + "\n" );
            }
        });
    }

}
