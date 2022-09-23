package com.ifx.client.app.pane;

import com.alibaba.fastjson.JSON;
import com.ifx.common.base.AccountInfo;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
        return;
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
    public static class AccountMiniPane extends Pane implements Initializable {

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
            init();
//            log.info("load {}  account {}" ,this.getClass().getName(),accountInfo);
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
            });
            log.info("load {}  account {}" ,this.getClass().getName(),accountInfo);

        }



    }
}
