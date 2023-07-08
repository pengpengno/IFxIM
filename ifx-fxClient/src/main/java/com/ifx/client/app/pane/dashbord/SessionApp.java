package com.ifx.client.app.pane.dashbord;

import cn.hutool.core.lang.Assert;
import com.google.inject.Singleton;
import com.ifx.client.api.SessionApi;
import com.ifx.client.app.pane.viewMain.ShowAction;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.context.AccountContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/2
 */

@Singleton
public class SessionApp extends DashBoardMiniApp{


    private Label applicationName;

    private String appName ;

    private Image applicationIcon ;

    @Autowired
    private ShowAction showAction;

    @Autowired
    SessionApi sessionApi;

    private SessionApp(){
        appName = "Session";
    }

    public String getAppName(){
        return appName;
    }


    public Mono<Void> initEvent(){
       return Mono.just(this)
                .doOnNext(e -> e.setOnMouseClicked(mouse -> initSessionInfoEvent().subscribe()))
               .then();
    }


    public Mono<Void> initSessionInfoEvent(){
        AccountInfo curAccount = AccountContext.getCurAccount();
        Assert.notNull(curAccount,"AccountInfo  is invalid , pls try login again!");
        return Mono.justOrEmpty(Optional.ofNullable(curAccount.getUserId()))
                .flatMap(id-> Mono.justOrEmpty(Optional.ofNullable(sessionApi.sessionInfo(id))))
                .flatMap(e-> showAction.showSession(e));
    }


    public Mono<Void> initPane(){
        return Mono.empty();
    }


}
