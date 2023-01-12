package com.ifx.client.service.helper;

import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson2.JSON;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import com.ifx.connect.proto.parse.DubboGenericParse;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class AccountHelper {

    private  static Protocol LOGINPROTOCOL = null;  //登陆协议

    private  static Protocol SEARCHPROTOCOL = null;  //搜索用户协议

    @SneakyThrows
    public static Protocol applyLogins(AccountBaseInfo vo){
        if (LOGINPROTOCOL !=null){
            return LOGINPROTOCOL;
        }
        Method search = com.ifx.account.service.AccountService.class.getMethod("search", AccountSearchVo.class);
        DubboApiMetaData metaData = DubboGenericParse.applyMeta0(com.ifx.account.service.AccountService.class, search, vo);
        Protocol protocol = new DubboProtocol();
        protocol.setProtocolBody(JSON.toJSONString(metaData));
        protocol.setType(IFxMsgProtocol.LOGIN_MSG_HEADER);
        LOGINPROTOCOL = protocol;
        return LOGINPROTOCOL;
    }




    /**
     * 提供所有账户的 Protocol
     * @param searchVo
     * @return
     */
    public static Protocol applySearchAccount(AccountSearchVo searchVo){
        try{
            if (SEARCHPROTOCOL !=null){
                return SEARCHPROTOCOL;
            }
            Method search = com.ifx.account.service.AccountService.class.getMethod("search", AccountSearchVo.class);
            return DubboGenericParse.applyMsgProtocol(search,searchVo);
        }catch (NoSuchMethodException noSuchMethodException){
            log.error("没有该方法");
            return null;
        }
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
