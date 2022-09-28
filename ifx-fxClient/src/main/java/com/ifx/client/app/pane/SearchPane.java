package com.ifx.client.app.pane;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.client.proxy.ProxyBean;
import com.ifx.common.ann.client.Proxy;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.context.AccountContext;
import com.ifx.session.service.ISessionAction;
import com.ifx.session.service.ISessionLifeStyle;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class SearchPane extends FlowPane {



    private List<AccountInfo> accountInfoList;

    private ConcurrentHashMap<String,AccountMiniPane> concurrentHashMap;

    private enum INSTANCE{
        INSTANCE;
        public final SearchPane instance ;
        INSTANCE(){
            instance = new SearchPane();
        }
        public static SearchPane getInstance(){
            return INSTANCE.instance;
        }
    }
    public static SearchPane getInstance(){
        return INSTANCE.getInstance();
    }

    public void init (){
        clear();
        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(15,15,15,15));
        accountInfoList.stream().forEach(e->{
            log.debug("search pane is loading ");
            AccountMiniPane accountMiniPane = new AccountMiniPane(e);
            getAccountMap().computeIfAbsent(e.getAccount(),(key)-> accountMiniPane);
        });
    }

    public void show (){
        init();
    }

    public void clear(){
        accountInfoList = null;
        concurrentHashMap = null;
    }

    private void initAccountMap(){
        if (concurrentHashMap == null){
            concurrentHashMap = new ConcurrentHashMap<>();
        }
    }
    public void setAccountInfoList(List<AccountInfo> accountInfos){
        accountInfoList = accountInfos;
    }



    private ConcurrentHashMap<String,AccountMiniPane> getAccountMap(){
        initAccountMap();
        return concurrentHashMap;
    }

    /**
     * 用户基本面板
     */
    @Data
    public static class AccountMiniPane extends Pane implements Initializable {
        @Proxy
        public ISessionLifeStyle lifeStyle;
        @Proxy
        public ISessionAction sessionAction;

        public AccountInfo accountInfo;

        private Label name;

        private ImageView iconView;

        private AccountMiniPane(){

        }
        public AccountMiniPane (AccountInfo accountInfo)
        {
            this.accountInfo = accountInfo;
            init();
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
//            init();
            log.info("load {}  account {}" ,this.getClass().getName(),accountInfo);
            Arrays.stream(this.getClass().getFields())
                    .filter(e-> ObjectUtil.equal(e.getAnnotatedType().getClass() ,Proxy.class))
                    .forEach(k-> {
                        k.setAccessible(true);
                        ReflectUtil.setFieldValue(this,k,ProxyBean.getProxyBean(k.getDeclaringClass()));
                    });
        }

        public static void main(String[] args) {
            AccountMiniPane accountMiniPane = new AccountMiniPane();
            Arrays.stream(AccountMiniPane.class.getFields())
                    .filter(e-> ObjectUtil.isNotNull(e.getAnnotationsByType(Proxy.class)))
                    .forEach(k-> {
                        k.setAccessible(true);
                        ReflectUtil.setFieldValue(accountMiniPane,k,ProxyBean.getProxyBean(k.getDeclaringClass()));
                    });
            System.out.println(accountMiniPane);
        }

        public void init(){
//        1.初始化标签文本
//        2.初始化容器大小
            name = new Label(accountInfo.getUserName());
            name.setVisible(true);
//            name.setText(accountInfo.getUserName());
            name.setLayoutX(20d);
            name.setLayoutY(10d);
            this.getChildren().add(name);
            this.addEventHandler(MouseEvent.MOUSE_CLICKED,(mouse)->{
                log.debug("click button the account is {}", JSON.toJSONString(accountInfo));
                HashSet<String> accountSet = CollectionUtil.newHashSet
                        (AccountContext.getCurAccount().getAccount(), accountInfo.getAccount());
                Long sessionId = lifeStyle.initialize(accountSet);
                log.info("创建session会话 {} ",sessionId);
            });
            log.info("load {}  account {}" ,this.getClass().getName(),accountInfo);

        }



    }
}
