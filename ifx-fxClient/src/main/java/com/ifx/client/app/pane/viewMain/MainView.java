package com.ifx.client.app.pane.viewMain;

import cn.hutool.core.lang.Assert;
import com.ifx.client.api.SessionApi;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.context.AccountContext;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/8
 */
@Component
public class MainView extends Pane   {

    @Autowired
    private List<MainViewAction> mainViewActions;

    private Pane currentView;


    @Autowired
    SessionApi sessionApi;

    @Autowired
    ShowAction showAction;

    public void switchPane( Pane view){
        int componentIndex = this.getChildren().indexOf(currentView);
        this.getChildren().remove(componentIndex);
        currentView = view;
        this.getChildren().add(componentIndex,currentView);
    }


    public void initPane() {

        currentView = new DefaultMainView();


        this.getChildren().add(currentView);


    }

    public Mono<Void> initEvent(){
        AccountInfo curAccount = AccountContext.getCurAccount();
        Assert.notNull(curAccount,"AccountInfo  is invalid , pls try login again!");
        return Mono.justOrEmpty(Optional.ofNullable(curAccount.getUserId()))
                .flatMap(id-> Mono.justOrEmpty(Optional.ofNullable(sessionApi.sessionInfo(id))))
                .flatMap(e-> showAction.showSession(e));
    }




}
